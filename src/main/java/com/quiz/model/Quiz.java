package com.quiz.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int qid;

    private String qName;
    private int qDurtion;
    private int totalQuestion;
    private String imageUrl;
    private float ratting;

    @ManyToOne
    @JoinColumn(name = "cid")  // Foreign key column
    private Category category;
}