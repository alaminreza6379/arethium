package com.torloksz.arethium.controller;

import com.torloksz.arethium.dto.GoalsDTO;
import com.torloksz.arethium.dto.MessageDTO;
import com.torloksz.arethium.entity.Users;
import com.torloksz.arethium.service.OnboardingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/onboarding")
public class OnboardingController {

    private final OnboardingService onboardingService;

    @GetMapping("/welcome")
    public String showWelcome() {
        Users user = onboardingService.getUserSession();
        System.out.println(user);
        if(user==null)
            return "redirect:/authorization/login";
        return user.getGoals()==null?"welcomePage":"redirect:/dashboard/home";
    }

    @GetMapping("/goals")
    public String showGoals(Model model) {
        model.addAttribute("goalsDTO",new GoalsDTO("",""));
        return "goalsPage";
    }

    @PostMapping("/goals")
    public String showGoalsForm(@Valid @ModelAttribute GoalsDTO goalsDTO, BindingResult bindingResult) {

        Users user = onboardingService.getUserSession();
        if(user==null)
            return "redirect:/authorization/login";
        if(bindingResult.hasErrors())
            return "redirect:/onboarding/goals";

        MessageDTO messageDTO = onboardingService.saveUserGoals(user.getEmail(), goalsDTO);

        return messageDTO.message().contains("Success")?"redirect:/dashboard/home":"redirect:/onboarding/goals";
    }

}
