package com.torloksz.arethium.entity;

import com.torloksz.arethium.dto.RegisterDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Email format is not proper..")
    @NotBlank(message = "Email can't be blank..")
    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false,length = 70)
    @NotBlank(message = "Name can't be blank..")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Password can't be blank")
    private String password;

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
    private Goals goals;


    public Users(RegisterDTO registerDTO,String password) {
        this.name = registerDTO.name();
        this.email = registerDTO.email();
        this.password = password;
    }
}
