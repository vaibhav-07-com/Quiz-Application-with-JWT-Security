package com.quiz.controller;

import com.quiz.dto.QuizSubmitRequestDto;
import com.quiz.dto.QuizSubmitResponseDto;
import com.quiz.dto.QuizAttemptResponseDto;
import com.quiz.service.QuizAttemptService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attempts")
public class QuizAttemptController {

    private final QuizAttemptService attemptService;

    @Autowired
    public QuizAttemptController(QuizAttemptService attemptService) {
        this.attemptService = attemptService;
    }

    // Start quiz attempt
    @PostMapping("/start/{quizId}")
    public ResponseEntity<QuizAttemptResponseDto> startQuiz(
            @PathVariable int quizId,
            @RequestParam int userId) {

        QuizAttemptResponseDto response =
                attemptService.startQuiz(quizId, userId);

        return ResponseEntity.ok(response);
    }

    // Submit quiz answers
    @PostMapping("/submit")
    public ResponseEntity<QuizSubmitResponseDto> submitQuiz(
            @Valid @RequestBody QuizSubmitRequestDto requestDto) {

        QuizSubmitResponseDto response =
                attemptService.submitQuiz(requestDto);

        return ResponseEntity.ok(response);
    }

    // Get attempt by id
    @GetMapping("/{attemptId}")
    public ResponseEntity<QuizAttemptResponseDto> getAttempt(
            @PathVariable int attemptId) {

        return ResponseEntity.ok(
                attemptService.getAttempt(attemptId)
        );
    }

    // Get all attempts of user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<QuizAttemptResponseDto>> getUserAttempts(
            @PathVariable int userId) {

        return ResponseEntity.ok(
                attemptService.getUserAttempts(userId)
        );
    }
}