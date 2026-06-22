package com.torloksz.arethium.controller;

import com.torloksz.arethium.dto.MessageDTO;
import com.torloksz.arethium.service.DashboardService;
import com.torloksz.arethium.service.InterviewGeneratorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final InterviewGeneratorService interviewGeneratorService;

    @GetMapping("/home")
    @Transactional
    public String showPage(Model model) {
        MessageDTO messageDTO = dashboardService.isUserSessionRunning();
        if(!messageDTO.message().contains("Success"))
            return "redirect:/authorization/login";

        Map<String,Object> stats = dashboardService.getStats();
        model.addAllAttributes(stats);
        return "homePage";
    }

    @GetMapping("/roadmap")
    public String showRoadmap(Model model) {
        MessageDTO messageDTO = dashboardService.isUserSessionRunning();
        if(!messageDTO.message().contains("Success"))
            return "redirect:/authorization/login";
        model.addAttribute("modules",dashboardService.getModules());
        return "roadmapPage";
    }

    @PostMapping("/roadmap/toggle-module/{id}")
    @Transactional
    public String toggleModule(@PathVariable Long id) {
        dashboardService.toggleModule(id);
        return "redirect:/dashboard/roadmap";
    }


    @GetMapping("/assessment/{id}")
    public String getAssessment(@PathVariable Long id,Model model) {
        MessageDTO messageDTO = dashboardService.isUserSessionRunning();
        if(!messageDTO.message().contains("Success"))
            return "redirect:/authorization/login";

        model.addAttribute("questions",dashboardService.takeAssessment(id));
        model.addAttribute("module",dashboardService.getModule(id));
        return "assessmentPage";
    }
    @PostMapping("/assessment/submit/{id}")
    public String submitAssessment(@PathVariable Long id, @RequestParam Map<String, String> allParams, Model model) {
        MessageDTO messageDTO = dashboardService.isUserSessionRunning();
        if(!messageDTO.message().contains("Success"))
            return "redirect:/authorization/login";

        double score = dashboardService.calculateScore(id, allParams);
        model.addAttribute("score", score);
        return "resultPage";
    }

    @GetMapping("/interview")
    public String interviewPage(Model model){
        MessageDTO messageDTO = dashboardService.isUserSessionRunning();
        if(!messageDTO.message().contains("Success"))
            return "redirect:/authorization/login";

        model.addAttribute("questions", dashboardService.generateInterview());

        return "interviewPage";
    }

    @PostMapping("/interview/submit")
    public String submitInterview(@RequestParam List<String> answers, Model model){
        Integer score = interviewGeneratorService.evaluateAnswers(answers);
        model.addAttribute("score", score);
        return "interviewResult";
    }
}
