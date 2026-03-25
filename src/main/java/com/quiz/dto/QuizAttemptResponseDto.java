package com.quiz.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuizAttemptResponseDto {

    private int attemptId;
    private int quizId;
    private String quizName;
    private int score;
    private int totalQuestions;
    private LocalDateTime attemptedAt;
}