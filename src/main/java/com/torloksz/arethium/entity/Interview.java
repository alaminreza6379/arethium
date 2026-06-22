package com.torloksz.arethium.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "interview")
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double score;

    @Column(length = 5000)
    private String feedback;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private Users users;

}