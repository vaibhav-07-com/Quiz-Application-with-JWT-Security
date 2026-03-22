package com.quiz.dto;

import lombok.Data;

@Data
public class QuizResponseDto {

    private int qid;
    private String qName;
    private int qDuration;
    private int totalQuestion;
    private String imageUrl;
    private float ratting;
    
    private int cid;
    private String categoryName;
}