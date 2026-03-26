package com.quiz.dto;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
	public class QuizSubmitResponseDto {

	    private int attemptId;
	    private int score;
	    private int totalQuestions;
	    private List<UserAnswerResponseDto> answers;
	
}
