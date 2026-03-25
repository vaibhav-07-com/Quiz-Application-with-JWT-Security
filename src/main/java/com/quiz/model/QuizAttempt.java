package com.quiz.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class QuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Many attempts can belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Many attempts can belong to one quiz
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private int score;

    private LocalDateTime attemptedAt = LocalDateTime.now();
}