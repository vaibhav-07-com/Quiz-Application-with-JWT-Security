package com.quiz.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Many answers belong to one attempt
    @ManyToOne
    @JoinColumn(name = "attempt_id")
    private QuizAttempt attempt;

    // Each answer is for one question
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private int selectedAnswer;
}