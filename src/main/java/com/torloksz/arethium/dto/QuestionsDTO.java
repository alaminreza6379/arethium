package com.torloksz.arethium.dto;

public record QuestionsDTO(
        String question,
        String optionA,
        String optionB,
        String optionC,
        String optionD,
        String correctAnswer
) {
}
