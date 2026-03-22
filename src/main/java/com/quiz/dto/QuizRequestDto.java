package com.quiz.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QuizRequestDto {

    @NotBlank(message = "Quiz name cannot be empty")
    private String qName;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int qDuration;

    @Min(value = 1, message = "Total questions must be at least 1")
    private int totalQuestion;

    private String imageUrl;

    private float ratting; // Optional: or set default 0
    
    @Min(value = 1, message = "Category id must be valid")
    private int cid;
}