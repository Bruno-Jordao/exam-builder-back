package com.example.exambuilder.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "question_alternatives")
public class QuestionAlternative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public QuestionAlternative() {
    }

    public QuestionAlternative(String text, Question question) {
        this.text = text;
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}