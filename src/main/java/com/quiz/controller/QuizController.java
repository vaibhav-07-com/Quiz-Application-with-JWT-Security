package com.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.quiz.dto.QuizRequestDto;
import com.quiz.dto.QuizResponseDto;
import com.quiz.service.QuizService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService){
        this.quizService = quizService;
    } 

    //Add new quiz
    @PostMapping
    public ResponseEntity<QuizResponseDto> addQuiz(@Valid @RequestBody QuizRequestDto quizRequestDto) {
        QuizResponseDto savedQuiz = quizService.addQuiz(quizRequestDto);
        return ResponseEntity.status(201).body(savedQuiz);
    }
    
    //Get All Quizzes
    @GetMapping
    public List<QuizResponseDto> getAllQuizzes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return quizService.getAllQuizzes(page, size);
    }
    
    //Get Quiz by ID
    @GetMapping("/{id}")
    public QuizResponseDto getQuizById(@PathVariable int id) {
        return quizService.getQuizById(id);
    }
    
    //Update Quiz
    @PutMapping("/{id}")
    public QuizResponseDto updateQuiz(
            @PathVariable int id,
            @Valid @RequestBody QuizRequestDto quizRequestDto) {
        return quizService.updateQuiz(id, quizRequestDto);
    }
    
    //Delete Quiz
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable int id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
    
    @GetMapping("/search")
    public List<QuizResponseDto> searchQuizzes(@RequestParam String name) {
        return quizService.searchQuizzes(name);
    }
}