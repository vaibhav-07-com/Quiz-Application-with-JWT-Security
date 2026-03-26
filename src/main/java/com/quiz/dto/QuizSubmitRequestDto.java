package com.quiz.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class QuizSubmitRequestDto {

    @NotNull(message = "Attempt ID is required")
    private Integer attemptId;

    @NotEmpty(message = "Answers cannot be empty")
    @Valid
    private List<UserAnswerRequestDto> answers;
}