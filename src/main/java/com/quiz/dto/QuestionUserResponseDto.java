package com.quiz.dto;

import java.util.List;

import lombok.Data;

@Data
public class QuestionUserResponseDto {

    private int id;
    private String questionText;
    private List<String> options;
    private int marks;
    private int timeLimit;
}