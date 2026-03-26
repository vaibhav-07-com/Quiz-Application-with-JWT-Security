package com.quiz.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class QuizAttemptRequestDto {

    @Min(value = 1, message = "User ID must be valid")
    private int userId;

    @Min(value = 1, message = "Quiz ID must be valid")
    private int quizId;
}