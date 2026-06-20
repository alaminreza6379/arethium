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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String targetRole;

    private String targetCompany;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users users;

}
