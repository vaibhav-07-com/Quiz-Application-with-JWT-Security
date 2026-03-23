package com.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quiz.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findByQuiz_Qid(int quizId);

    List<Question> findByQuestionTextContainingIgnoreCase(String keyword);
}