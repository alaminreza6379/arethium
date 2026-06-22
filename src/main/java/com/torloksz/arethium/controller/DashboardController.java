package com.torloksz.arethium.controller;

import com.torloksz.arethium.dto.MessageDTO;
import com.torloksz.arethium.service.DashboardService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

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

}
