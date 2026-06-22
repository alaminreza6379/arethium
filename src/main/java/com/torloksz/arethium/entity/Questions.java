package com.torloksz.arethium.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "questions")
@Getter
@Setter
@RequiredArgsConstructor
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String optionA, optionB,optionC,optionD;
    private String correctAnswer;

    @ManyToOne
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    public Questions(String question, String optionA, String optionB,String optionC,String optionD, String correctAnswer) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }
}
