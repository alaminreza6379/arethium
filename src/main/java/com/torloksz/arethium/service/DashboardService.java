package com.torloksz.arethium.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.torloksz.arethium.dto.*;
import com.torloksz.arethium.entity.*;
import com.torloksz.arethium.repository.AssessmentRepository;
import com.torloksz.arethium.repository.InterviewRepository;
import com.torloksz.arethium.repository.ModulesRepository;
import com.torloksz.arethium.repository.QuestionsRepository;
import com.torloksz.arethium.session.UserSession;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserSession userSession;
    private final ObjectMapper objectMapper;
    Model model;
    private final ModulesRepository modulesRepository;
    private final AssessmentRepository assessmentRepository;
    private final AssessmentGenerator assessmentGenerator;
    private final QuestionsRepository questionsRepository;
    private final InterviewRepository interviewRepository;
    private final InterviewGeneratorService interviewGeneratorService;

    public MessageDTO isUserSessionRunning() {
        Users users = userSession.getUser();
        if(users==null)
            return new MessageDTO("User is not Logged in..");
        return new MessageDTO("Success");
    }

    public Map<String, Object> getStats() {
        if (isUserSessionRunning().message().contains("Success")){

            Users user = userSession.getUser();
            Map<String, Object> stats = new HashMap<>();
            stats.put("targetRole", user.getGoals().getTargetRole());
            stats.put("targetCompany", user.getGoals().getTargetCompany());
            stats.put("userName", user.getName());
            stats.put("readiness", findReadiness(user));
            stats.put("rank", findRank(user));
            stats.put("time", findTime(user));
            stats.put("modules", getModules(user));

            return stats;
        }
        return new HashMap<>();
    }

    public double findReadiness(Users user) {
        List<Modules> modules = modulesRepository.findByUsersIdOrderByModuleOrderAsc(user.getId());
        long completed = modules.stream()
                .filter(Modules::isCompleted)
                .count();
        return (((double) completed /modules.size())*100);
    }

    public String findRank(Users user) {
        double completed = findReadiness(user);
        if(completed<=20) return "D";
        else if (completed<=50) return "C";
        else if (completed<=65) return "B";
        else if (completed<=80) return "A";
        else return "S";
    }

    public long findTime(Users user) {
        List<Modules> modules = modulesRepository.findByUsersIdOrderByModuleOrderAsc(user.getId());
        long timeLeft=0;
        for (Modules m:modules){
            if (!m.isCompleted())
                timeLeft+=m.getTime();
        }
        return timeLeft;
    }

    public List<Modules> getModules(Users user) {
        return modulesRepository.findByUsersIdOrderByModuleOrderAsc(user.getId());
    }

    public List<Modules> getModules() {
        return modulesRepository.findByUsersIdOrderByModuleOrderAsc(userSession.getUser().getId());
    }

    public void toggleModule(Long id) {
        Modules modules = modulesRepository.findModulesById(id);
        if (assessmentRepository.findAssessmentsByModulesId(id).isEmpty())
            generateAssessment(id);
        if (!assessmentRepository.findAssessmentsByModulesId(id).get().getPassed())
            return;
        modules.setCompleted(true);
        modulesRepository.save(modules);
    }

    public void generateAssessment(Long id) {
        try{
            if (assessmentRepository.findAssessmentsByModulesId(id).isPresent())
                return;
            String json = assessmentGenerator.generateAssessment(modulesRepository.findModulesById(id).getDescription(),
                    modulesRepository.findModulesById(id).getTitle());
            String cleanJson = json.replaceAll("```json|```","").trim();
            AssessmentsResponseDTO questions = objectMapper.readValue(cleanJson, AssessmentsResponseDTO.class);

            Assessment assessment = new Assessment();
            assessment.setModules(modulesRepository.findModulesById(id));
            assessment.setPassed(false);
            assessment.setScore(0.0);
            assessmentRepository.save(assessment);

            for(QuestionsDTO q : questions.questions()){
                Questions question = new Questions(q.question(),q.optionA(),q.optionB(),q.optionC(),q.optionD(),q.correctAnswer());
                question.setAssessment(assessment);
                questionsRepository.save(question);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public List<Questions> takeAssessment(Long id){
        return assessmentRepository.findAssessmentsByModulesId(id).get().getQuestions();
    }

    public Modules getModule(Long id) {
        return modulesRepository.findModulesById(id);
    }

    public double calculateScore(Long id,Map<String, String> answers) {
        List<Questions> questions = assessmentRepository.findAssessmentsByModulesId(id).get().getQuestions();
        int correct = 0;

        for(Questions question : questions) {
            String ans = answers.get("q"+ question.getId());
            if (ans!=null && ans.equals(question.getCorrectAnswer()))
                correct++;
        }

        double score = (((double) correct/ questions.size())*100);
        Assessment assessment = assessmentRepository.getAssessmentByModulesId(id);
        assessment.setScore(score);
        assessment.setPassed(score>=85.0);
        assessmentRepository.save(assessment);

        if (assessment.getPassed()){
            Modules module = modulesRepository.findModulesById(id);
            module.setCompleted(true);
            modulesRepository.save(module);
        }

        return score;
    }

    public List<String> generateInterview() {
        Users user = userSession.getUser();
        try {
            String json =interviewGeneratorService.generateQuestions(user.getGoals().getTargetRole());

            String clean =json.replaceAll("```json|```", "").trim();

            JsonNode node =objectMapper.readTree(clean);

            List<String> questions =new ArrayList<>();

            for(JsonNode q : node.get("questions")) {

                questions.add(q.asText());
            }
            return questions;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
