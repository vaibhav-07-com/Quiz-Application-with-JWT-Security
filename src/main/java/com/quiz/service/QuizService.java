package com.quiz.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quiz.dto.QuizRequestDto;
import com.quiz.dto.QuizResponseDto;
import com.quiz.model.Category;
import com.quiz.model.Quiz;
import com.quiz.repository.CategoryRepository;
import com.quiz.repository.QuizRepository;

@Service
public class QuizService {

    private final QuizRepository quizRepo;

    @Autowired
    public QuizService(QuizRepository quizRepo) {
        this.quizRepo = quizRepo;
    }
    
    @Autowired
    private CategoryRepository categoryRepository;

    public QuizResponseDto addQuiz(QuizRequestDto quizDto) {

        // 1️⃣ Fetch category using cid
        Category category = categoryRepository.findById(quizDto.getCid())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // 2️⃣ Map request DTO to entity
        Quiz quiz = new Quiz();
        quiz.setQName(quizDto.getQName());
        quiz.setQDurtion(quizDto.getQDuration());
        quiz.setTotalQuestion(quizDto.getTotalQuestion());
        quiz.setImageUrl(quizDto.getImageUrl());
        quiz.setRatting(quizDto.getRatting());
        quiz.setCategory(category);

        // 3️⃣ Save entity
        Quiz savedQuiz = quizRepo.save(quiz);

        // 4️⃣ Map saved entity to response DTO
        QuizResponseDto response = new QuizResponseDto();
        response.setQid(savedQuiz.getQid());
        response.setQName(savedQuiz.getQName());
        response.setQDuration(savedQuiz.getQDurtion());
        response.setTotalQuestion(savedQuiz.getTotalQuestion());
        response.setImageUrl(savedQuiz.getImageUrl());
        response.setRatting(savedQuiz.getRatting());

        // category info
        response.setCid(savedQuiz.getCategory().getCid());
        response.setCategoryName(savedQuiz.getCategory().getCName());

        return response;
    }
    
    public List<QuizResponseDto> getAllQuizzes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Quiz> quizzes = quizRepo.findAll(pageable);

        return quizzes.stream().map(quiz -> {
            QuizResponseDto dto = new QuizResponseDto();
            dto.setQid(quiz.getQid());
            dto.setQName(quiz.getQName());
            dto.setQDuration(quiz.getQDurtion());
            dto.setTotalQuestion(quiz.getTotalQuestion());
            dto.setImageUrl(quiz.getImageUrl());
            dto.setRatting(quiz.getRatting());
            return dto;
        }).toList();
    }
    
    public QuizResponseDto getQuizById(int id) {
        Quiz quiz = quizRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));

        QuizResponseDto dto = new QuizResponseDto();
        dto.setQid(quiz.getQid());
        dto.setQName(quiz.getQName());
        dto.setQDuration(quiz.getQDurtion());
        dto.setTotalQuestion(quiz.getTotalQuestion());
        dto.setImageUrl(quiz.getImageUrl());
        dto.setRatting(quiz.getRatting());

        return dto;
    }
    
    public QuizResponseDto updateQuiz(int id, QuizRequestDto dto) {
        Quiz existingQuiz = quizRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));

        existingQuiz.setQName(dto.getQName());
        existingQuiz.setQDurtion(dto.getQDuration());
        existingQuiz.setTotalQuestion(dto.getTotalQuestion());
        existingQuiz.setImageUrl(dto.getImageUrl());
        existingQuiz.setRatting(dto.getRatting());

        Quiz updatedQuiz = quizRepo.save(existingQuiz);

        QuizResponseDto response = new QuizResponseDto();
        response.setQid(updatedQuiz.getQid());
        response.setQName(updatedQuiz.getQName());
        response.setQDuration(updatedQuiz.getQDurtion());
        response.setTotalQuestion(updatedQuiz.getTotalQuestion());
        response.setImageUrl(updatedQuiz.getImageUrl());
        response.setRatting(updatedQuiz.getRatting());

        return response;
    }
    
    public void deleteQuiz(int id) {
        Quiz existingQuiz = quizRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));
        quizRepo.delete(existingQuiz);
    }
    
    public List<QuizResponseDto> searchQuizzes(String name) {
        List<Quiz> quizzes = quizRepo.findByQNameContainingIgnoreCase(name);
        return quizzes.stream().map(quiz -> {
            QuizResponseDto dto = new QuizResponseDto();
            dto.setQid(quiz.getQid());
            dto.setQName(quiz.getQName());
            dto.setQDuration(quiz.getQDurtion());
            dto.setTotalQuestion(quiz.getTotalQuestion());
            dto.setImageUrl(quiz.getImageUrl());
            dto.setRatting(quiz.getRatting());
            return dto;
        }).toList();
    }
}