package com.quiz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.quiz.dto.QuestionRequestDto;
import com.quiz.dto.QuestionResponseDto;
import com.quiz.model.Question;
import com.quiz.model.Quiz;
import com.quiz.enums.DifficultyLevel;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;

@Service
public class QuestionService {

    private final QuestionRepository questionRepo;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepo) {
        this.questionRepo = questionRepo;
    }

    // ✅ Add Question
    public QuestionResponseDto addQuestion(QuestionRequestDto dto) {

        // 1️⃣ Fetch Quiz
        Quiz quiz = quizRepository.findById(dto.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        // 2️⃣ Map DTO → Entity
        Question question = new Question();
        question.setQuestionText(dto.getQuestionText());
        question.setOptions(dto.getOptions());
        question.setCorrectOption(dto.getCorrectOption());
        question.setDifficultyLevel(DifficultyLevel.valueOf(dto.getDifficultyLevel().toUpperCase()));
        question.setMarks(dto.getMarks());
        question.setTimeLimit(dto.getTimeLimit());
        question.setExplanation(dto.getExplanation());
        question.setQuiz(quiz);

        // 3️⃣ Save
        Question saved = questionRepo.save(question);

        // 4️⃣ Return DTO
        return mapToResponse(saved);
    }

    // ✅ Get All Questions (Pagination)
    public List<QuestionResponseDto> getAllQuestions(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Question> questions = questionRepo.findAll(pageable);

        return questions.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ✅ Get by ID
    public QuestionResponseDto getQuestionById(int id) {
        Question question = questionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));

        return mapToResponse(question);
    }

    // ✅ Get by Quiz ID
    public List<QuestionResponseDto> getQuestionsByQuizId(int quizId) {
        List<Question> questions = questionRepo.findByQuiz_Qid(quizId);

        return questions.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ✅ Update Question
    public QuestionResponseDto updateQuestion(int id, QuestionRequestDto dto) {

        Question existing = questionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));

        Quiz quiz = quizRepository.findById(dto.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        existing.setQuestionText(dto.getQuestionText());
        existing.setOptions(dto.getOptions());
        existing.setCorrectOption(dto.getCorrectOption());
        existing.setDifficultyLevel(DifficultyLevel.valueOf(dto.getDifficultyLevel().toUpperCase()));
        existing.setMarks(dto.getMarks());
        existing.setTimeLimit(dto.getTimeLimit());
        existing.setExplanation(dto.getExplanation());
        existing.setQuiz(quiz);

        Question updated = questionRepo.save(existing);

        return mapToResponse(updated);
    }

    // ✅ Delete Question
    public void deleteQuestion(int id) {
        Question question = questionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));

        questionRepo.delete(question);
    }

    // ✅ Search Questions
    public List<QuestionResponseDto> searchQuestions(String keyword) {
        List<Question> questions = questionRepo
                .findByQuestionTextContainingIgnoreCase(keyword);

        return questions.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ✅ Common Mapper (Reusable)
    private QuestionResponseDto mapToResponse(Question q) {
        QuestionResponseDto dto = new QuestionResponseDto();

        dto.setId(q.getId());
        dto.setQuestionText(q.getQuestionText());
        dto.setOptions(q.getOptions());
        dto.setCorrectOption(q.getCorrectOption());
        dto.setDifficultyLevel(q.getDifficultyLevel().name());
        dto.setMarks(q.getMarks());
        dto.setTimeLimit(q.getTimeLimit());
        dto.setExplanation(q.getExplanation());
        dto.setQuizId(q.getQuiz().getQid());

        return dto;
    }
}