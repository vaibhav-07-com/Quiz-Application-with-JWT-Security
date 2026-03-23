package com.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.quiz.dto.QuestionRequestDto;
import com.quiz.dto.QuestionResponseDto;
import com.quiz.service.QuestionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // Add new question
    @PostMapping
    public ResponseEntity<QuestionResponseDto> addQuestion(
            @Valid @RequestBody QuestionRequestDto questionRequestDto) {

        QuestionResponseDto savedQuestion = questionService.addQuestion(questionRequestDto);
        return ResponseEntity.status(201).body(savedQuestion);
    }

    // Get all questions (with pagination)
    @GetMapping
    public List<QuestionResponseDto> getAllQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return questionService.getAllQuestions(page, size);
    }

    // Get question by ID
    @GetMapping("/{id}")
    public QuestionResponseDto getQuestionById(@PathVariable int id) {
        return questionService.getQuestionById(id);
    }

    // Get questions by quiz ID
    @GetMapping("/quiz/{quizId}")
    public List<QuestionResponseDto> getQuestionsByQuizId(@PathVariable int quizId) {
        return questionService.getQuestionsByQuizId(quizId);
    }

    // Update question
    @PutMapping("/{id}")
    public QuestionResponseDto updateQuestion(
            @PathVariable int id,
            @Valid @RequestBody QuestionRequestDto questionRequestDto) {

        return questionService.updateQuestion(id, questionRequestDto);
    }

    // Delete question
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable int id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    // Search questions by text
    @GetMapping("/search")
    public List<QuestionResponseDto> searchQuestions(@RequestParam String keyword) {
        return questionService.searchQuestions(keyword);
    }
}