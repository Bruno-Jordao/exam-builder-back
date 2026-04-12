package com.example.exambuilder.web.dto;

public class QuestionAlternativeDto {

    private String text;

    public QuestionAlternativeDto() {
    }

    public QuestionAlternativeDto(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}