package com.quiz.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UserAnswerRequestDto {

    @Min(value = 1, message = "Question ID must be valid")
    private int questionId;

    @Min(value = 1, message = "Selected answer must be valid")
    private int selectedAnswer;
}