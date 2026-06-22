package com.torloksz.arethium.dto;


import java.util.List;

public record AssessmentsResponseDTO(
        List<QuestionsDTO> questions
) {
}
