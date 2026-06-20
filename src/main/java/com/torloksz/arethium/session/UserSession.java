package com.torloksz.arethium.session;

import com.torloksz.arethium.entity.Users;
import com.torloksz.arethium.repository.UsersRepository;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@Data
public class UserSession {
    private Users user;
    private final UsersRepository usersRepository;

    public void init(String email) {
        this.user = usersRepository.findByEmail(email).get();
    }
}
