package com.torloksz.arethium.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "goals")
@Getter
@Setter
@RequiredArgsConstructor
public class Goals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String targetRole;

    @Column(nullable = false)
    private String targetCompany;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users users;

    public Goals(String targetRole,String targetCompany) {
        this.targetCompany = targetCompany;
        this.targetRole = targetRole;
    }
}
