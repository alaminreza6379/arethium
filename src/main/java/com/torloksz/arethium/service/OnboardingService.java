package com.torloksz.arethium.service;

import com.torloksz.arethium.dto.GoalsDTO;
import com.torloksz.arethium.dto.MessageDTO;
import com.torloksz.arethium.entity.Goals;
import com.torloksz.arethium.entity.Users;
import com.torloksz.arethium.repository.GoalsRepository;
import com.torloksz.arethium.repository.UsersRepository;
import com.torloksz.arethium.session.UserSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OnboardingService {

    private final GoalsRepository goalsRepository;
    private final UsersRepository usersRepository;
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
            Users user = usersRepository.findByEmail(email).orElseThrow();
            Goals goals = new Goals(goalsDTO.targetRole(), goalsDTO.targetCompany());
            goals = goalsRepository.save(goals);

            user.setGoals(goals);
            usersRepository.save(user);
            return new MessageDTO("Success");
        } catch (RuntimeException e) {
            throw new RuntimeException("Unexpected Error Occurred;");
        }
    }
}
