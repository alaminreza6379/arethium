package com.torloksz.arethium.service;

import com.torloksz.arethium.dto.GoalsDTO;
import com.torloksz.arethium.dto.MessageDTO;
import com.torloksz.arethium.dto.ModuleResponseDTO;
import com.torloksz.arethium.dto.RoadmapResponseDTO;
import com.torloksz.arethium.entity.Goals;

import com.torloksz.arethium.entity.Modules;
import com.torloksz.arethium.entity.Users;
import com.torloksz.arethium.repository.GoalsRepository;
import com.torloksz.arethium.repository.ModulesRepository;
import com.torloksz.arethium.repository.UsersRepository;
import com.torloksz.arethium.session.UserSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class OnboardingService {

    private final GoalsRepository goalsRepository;
    private final UsersRepository usersRepository;
    private final ModulesRepository modulesRepository;
    private final RoadmapGeneratorService geminiService;
    private final ObjectMapper objectMapper;
    private final UserSession userSession;

    public Users findByEmail(String email) {
        return usersRepository.findByEmail(email).get();
    }


    public Users getUserSession() {
        return userSession.getUser();
    }

    @Transactional
    public MessageDTO saveUserGoals(String email, GoalsDTO goalsDTO) {
        try {
            if (goalsDTO.targetRole() == null)
                return new MessageDTO("Please Select your targetRole..");
            if (goalsDTO.targetCompany() == null)
                return new MessageDTO("Please Select your targetCompany..");
            Users user = usersRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User Not Found"));
            Goals goals = new Goals(goalsDTO.targetRole(), goalsDTO.targetCompany());
            goals.setUsers(user);
            user.setGoals(goals);
            usersRepository.save(user);
            generateAndSaveRoadmap(user, goalsDTO.targetRole(), goalsDTO.targetCompany());
            return new MessageDTO("Success");
        } catch (RuntimeException e) {
            throw new RuntimeException("Unexpected Error Occurred;");
        }
    }

    public void generateAndSaveRoadmap(Users user,String targetRole,String targetCompany){
        try{
            modulesRepository.deleteByUsers(user);
            String json = geminiService.generateRoadmap(targetRole,targetCompany);
            System.out.println(json);
            String cleanJson = json.replaceAll("```json|```", "").trim();
            RoadmapResponseDTO roadmap = objectMapper.readValue(cleanJson,RoadmapResponseDTO.class);

            for(ModuleResponseDTO m : roadmap.modules()){
                Modules modules = new Modules(user,m.order(),m.title(),m.description(),false);
                modulesRepository.save(modules);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected Error");
        }
    }
}
