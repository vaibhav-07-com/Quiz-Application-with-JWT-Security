package com.quiz.dto;

import java.util.List;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class QuestionRequestDto {

    @NotBlank(message = "Question text cannot be empty")
    private String questionText;

    @NotNull(message = "Options cannot be null")
    @Size(min = 2, max = 5, message = "There must be between 2 and 5 options")
    private List<@NotBlank(message = "Option cannot be empty") String> options;

    @Min(value = 1, message = "Correct option must be at least 1")
    private int correctOption; // 1-based index

    @NotBlank(message = "Difficulty level is required")
    private String difficultyLevel; // EASY, MEDIUM, HARD

    @Min(value = 1, message = "Marks must be at least 1")
    private int marks;

    @Min(value = 5, message = "Time limit must be at least 5 seconds")
    private int timeLimit; // in seconds

    private String explanation;

    @Min(value = 1, message = "Quiz ID must be valid")
    private int quizId;
}