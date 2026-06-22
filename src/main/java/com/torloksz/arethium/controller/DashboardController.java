package com.torloksz.arethium.controller;

import com.torloksz.arethium.dto.MessageDTO;
import com.torloksz.arethium.service.DashboardService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

}
