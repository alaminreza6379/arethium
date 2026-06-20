package com.torloksz.arethium.controller;

import com.torloksz.arethium.dto.LoginDTO;
import com.torloksz.arethium.dto.MessageDTO;
import com.torloksz.arethium.dto.RegisterDTO;
import com.torloksz.arethium.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
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
    private final AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerDTO") RegisterDTO registerDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("message",bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/authorization/register";
        }

        MessageDTO messageDTO = authService.register(registerDTO);
        redirectAttributes.addFlashAttribute("message",messageDTO.message());

        if(messageDTO.message().contains("Success")) {
            authenticateUser(registerDTO,request);
            return "redirect:/onboarding/welcome";
        }
        return "redirect:/authorization/login";
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

    private void authenticateUser(RegisterDTO registerDTO, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(registerDTO.email(), registerDTO.password());
        Authentication authentication = authenticationManager.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }
}
