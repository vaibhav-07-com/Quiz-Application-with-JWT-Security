package com.quiz.dto;

import java.util.List;

import lombok.Data;

@Data
public class QuestionResponseDto {

    private int id;
    private String questionText;
    private List<String> options;
    private int correctOption;
    private String difficultyLevel;
    private int marks;
    private int timeLimit;
    private String explanation;
    private int quizId;
}