package com.quiz.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.quiz.dto.*;
import com.quiz.enums.DifficultyLevel;
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
    
    public List<QuestionResponseDto> getQuestionsForAttempt(int attemptId) {
        // 1️⃣ Fetch the attempt
        QuizAttempt attempt = attemptRepo.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        int totalQuestions = 10; // or pass some default number
        int easyCount = (int) Math.round(totalQuestions * 0.7);
        int mediumCount = (int) Math.round(totalQuestions * 0.2);
        int hardCount = totalQuestions - easyCount - mediumCount; // remaining

        // 2️⃣ Fetch questions by difficulty
        List<Question> easyQuestions = questionRepo.findByQuiz_QidAndDifficultyLevel(
                attempt.getQuiz().getQid(), DifficultyLevel.EASY, PageRequest.of(0, easyCount));
        List<Question> mediumQuestions = questionRepo.findByQuiz_QidAndDifficultyLevel(
                attempt.getQuiz().getQid(), DifficultyLevel.MEDIUM, PageRequest.of(0, mediumCount));
        List<Question> hardQuestions = questionRepo.findByQuiz_QidAndDifficultyLevel(
                attempt.getQuiz().getQid(), DifficultyLevel.HARD, PageRequest.of(0, hardCount));

        // 3️⃣ Combine all questions
        List<Question> allQuestions = new ArrayList<>();
        allQuestions.addAll(easyQuestions);
        allQuestions.addAll(mediumQuestions);
        allQuestions.addAll(hardQuestions);

        // 4️⃣ Shuffle the list so questions are in random order
        Collections.shuffle(allQuestions);

        // 5️⃣ Convert to DTO
        List<QuestionResponseDto> list = allQuestions.stream().map(q -> {
            QuestionResponseDto dto = new QuestionResponseDto();
            dto.setId(q.getId());
            dto.setQuestionText(q.getQuestionText());
            dto.setOptions(q.getOptions());
            dto.setDifficultyLevel(q.getDifficultyLevel().name());
            dto.setMarks(q.getMarks());
            dto.setTimeLimit(q.getTimeLimit());
            return dto;
        }).collect(Collectors.toList());

        return list;
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