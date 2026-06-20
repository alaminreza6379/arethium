package com.torloksz.arethium.service;

import com.torloksz.arethium.dto.LoginDTO;
import com.torloksz.arethium.dto.MessageDTO;
import com.torloksz.arethium.dto.RegisterDTO;
import com.torloksz.arethium.entity.Users;
import com.torloksz.arethium.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository usersRepository;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,63}$");

    public boolean isEmail(String input) {
        return input != null && EMAIL_PATTERN.matcher(input).matches();
    }

    public MessageDTO register(RegisterDTO registerDTO) {
        try{
            if (usersRepository.findByEmail(registerDTO.email()).isPresent())
                return new MessageDTO("Email is already registered with us..");
            if (!(registerDTO.password().equals(registerDTO.confirmPassword())))
                return new MessageDTO("Confirm your password");
            if(!isEmail(registerDTO.email()))
                return new MessageDTO("Email format should be proper...");
            usersRepository.save(new Users(registerDTO,passwordEncoder().encode(registerDTO.password())));
            return new MessageDTO("Registration is successful..");
        } catch (Exception e) {
            throw new RuntimeException("Details provided do not meet the requirement of information we want..");
        }
    }

    public MessageDTO login(LoginDTO loginDTO) {
        try {
            var user  = usersRepository.findByEmail(loginDTO.email());
            if (user.isPresent()) {
                Users users = user.get();
                boolean hasGoals = (users.getGoals()!=null);
                if(passwordEncoder().matches(loginDTO.password(),usersRepository.findByEmail(loginDTO.email()).get().getPassword()))
                    return new MessageDTO("Success "+hasGoals);
                else
                    return new MessageDTO("Password doesn't match "+loginDTO.email());
            }
            else
                return new MessageDTO("Email not registered.Register please..");
        } catch (RuntimeException e) {
            throw new RuntimeException("Error."+loginDTO.email());
        }
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
