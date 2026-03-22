package com.quiz.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.model.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
	
	List<Quiz> findByQNameContainingIgnoreCase(String name);

}
