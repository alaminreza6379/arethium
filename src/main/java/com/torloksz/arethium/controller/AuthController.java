package com.torloksz.arethium.controller;

import com.torloksz.arethium.dto.LoginDTO;
import com.torloksz.arethium.dto.MessageDTO;
import com.torloksz.arethium.dto.RegisterDTO;
import com.torloksz.arethium.entity.Users;
import com.torloksz.arethium.service.AuthService;
import com.torloksz.arethium.session.UserSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/authorization")
public class AuthController {


    private final AuthService authService;
    private final UserSession userSession;


    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerDTO") RegisterDTO registerDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("message",bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/authorization/register";
        }

        MessageDTO messageDTO = authService.register(registerDTO);
        redirectAttributes.addFlashAttribute("message",messageDTO.message());

        if(messageDTO.message().contains("Success")) {
            userSession.init(registerDTO.email());
            return "redirect:/onboarding/welcome";
        }
        return "redirect:/authorization/register";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginDTO") LoginDTO loginDTO,BindingResult bindingResult,RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("message",bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/authorization/login";
        }

        MessageDTO messageDTO = authService.login(loginDTO);
        redirectAttributes.addFlashAttribute("message",messageDTO.message());

        if (messageDTO.message().contains("Success")) {
            userSession.init(loginDTO.email());
            if (messageDTO.message().contains("true")){
                return "redirect:/dashboard/home";
            }
            else
                return "redirect:/onboarding/welcome";
        }
        else
            return "redirect:/authorization/login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerDTO",new RegisterDTO("","","",""));
        return "registrationPage";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginDTO",new LoginDTO("",""));
        return "loginPage";
    }

    @PostMapping("/logout")
    public String logout() {
        userSession.setUser(new Users());
        return "redirect:/authorization/login";
    }
}
