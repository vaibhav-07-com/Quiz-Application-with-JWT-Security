package com.quiz.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quiz.dto.*;
import com.quiz.model.*;
import com.quiz.repository.*;

@Service
public class QuizAttemptService {

    @Autowired
    private QuizAttemptRepository attemptRepo;

    @Autowired
    private UserAnswerRepository answerRepo;

    @Autowired
    private QuizRepository quizRepo;

    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private UserRepository userRepo;

    // START QUIZ
    public QuizAttemptResponseDto startQuiz(int quizId, int userId) {

        Quiz quiz = quizRepo.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        QuizAttempt attempt = new QuizAttempt();
        attempt.setQuiz(quiz);
        attempt.setUser(user);
        attempt.setScore(0);
        attempt.setAttemptedAt(LocalDateTime.now());

        QuizAttempt saved = attemptRepo.save(attempt);

        QuizAttemptResponseDto dto = new QuizAttemptResponseDto();
        dto.setAttemptId(saved.getId());
        dto.setQuizId(quiz.getQid());
        dto.setQuizName(quiz.getQName());
        dto.setScore(0);
        dto.setTotalQuestions(quiz.getTotalQuestion());
        dto.setAttemptedAt(saved.getAttemptedAt());

        return dto;
    }

    // SUBMIT QUIZ
    public QuizSubmitResponseDto submitQuiz(QuizSubmitRequestDto requestDto) {

        QuizAttempt attempt = attemptRepo.findById(requestDto.getAttemptId())
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        int score = 0;
        List<UserAnswerResponseDto> responseList = new ArrayList<>();

        for (UserAnswerRequestDto ansDto : requestDto.getAnswers()) {

            Question question = questionRepo.findById(ansDto.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            // save user answer
            UserAnswer answer = new UserAnswer();
            answer.setAttempt(attempt);
            answer.setQuestion(question);
            answer.setSelectedAnswer(ansDto.getSelectedAnswer());

            answerRepo.save(answer);

            // check correct
            boolean isCorrect =
                    question.getCorrectOption() == ansDto.getSelectedAnswer();

            if (isCorrect) score++;

            responseList.add(
                    new UserAnswerResponseDto(
                            question.getId(),
                            ansDto.getSelectedAnswer(),
                            question.getCorrectOption(),
                            isCorrect
                    )
            );
        }

        attempt.setScore(score);
        attemptRepo.save(attempt);

        return new QuizSubmitResponseDto(
                attempt.getId(),
                score,
                requestDto.getAnswers().size(),
                responseList
        );
    }

    // GET ATTEMPT
    public QuizAttemptResponseDto getAttempt(int attemptId) {

        QuizAttempt attempt = attemptRepo.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        QuizAttemptResponseDto dto = new QuizAttemptResponseDto();
        dto.setAttemptId(attempt.getId());
        dto.setQuizId(attempt.getQuiz().getQid());
        dto.setQuizName(attempt.getQuiz().getQName());
        dto.setScore(attempt.getScore());
        dto.setTotalQuestions(attempt.getQuiz().getTotalQuestion());
        dto.setAttemptedAt(attempt.getAttemptedAt());

        return dto;
    }

    // USER HISTORY
    public List<QuizAttemptResponseDto> getUserAttempts(int userId) {

        List<QuizAttempt> attempts =
                attemptRepo.findByUserId(userId);

        List<QuizAttemptResponseDto> list = new ArrayList<>();

        for (QuizAttempt attempt : attempts) {

            QuizAttemptResponseDto dto =
                    new QuizAttemptResponseDto();

            dto.setAttemptId(attempt.getId());
            dto.setQuizId(attempt.getQuiz().getQid());
            dto.setQuizName(attempt.getQuiz().getQName());
            dto.setScore(attempt.getScore());
            dto.setTotalQuestions(
                    attempt.getQuiz().getTotalQuestion()
            );
            dto.setAttemptedAt(attempt.getAttemptedAt());

            list.add(dto);
        }

        return list;
    }
}