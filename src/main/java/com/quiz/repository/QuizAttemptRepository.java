package com.quiz.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.quiz.model.QuizAttempt;


public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Integer> {
	
	List<QuizAttempt> findByUserId(int userId);

}