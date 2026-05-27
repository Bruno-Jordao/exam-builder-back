package com.example.exambuilder.exceptions;

public class QuestionAlternativeNotFoundException extends RuntimeException {
    public QuestionAlternativeNotFoundException(String message) {
        super(message);
    }
}