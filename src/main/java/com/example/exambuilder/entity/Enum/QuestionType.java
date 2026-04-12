package com.example.exambuilder.entity.Enum;

public enum QuestionType {
    ABERTA(1),
    FECHADA(2);

    private final int valor;

    QuestionType(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}