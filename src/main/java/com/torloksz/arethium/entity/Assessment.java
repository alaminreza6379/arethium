package com.torloksz.arethium.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "assessment")
@Setter
@Getter
@RequiredArgsConstructor
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "module_id")
    private Modules modules;

    @OneToMany(mappedBy = "assessment",cascade = CascadeType.ALL)
    private List<Questions> questions;

    private Double score;

    private Boolean passed;
}
