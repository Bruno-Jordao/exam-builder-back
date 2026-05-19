package com.example.exambuilder.exceptions;

public class EvaluationNotFoundException extends RuntimeException {
    public EvaluationNotFoundException(String message) {
        super(message);
    }
}