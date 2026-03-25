package com.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAnswerResponseDto {

    private int questionId;
    private int selectedAnswer;
    private int correctAnswer;
    private boolean isCorrect;
}