package com.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quiz.model.UserAnswer;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Integer> {
}
