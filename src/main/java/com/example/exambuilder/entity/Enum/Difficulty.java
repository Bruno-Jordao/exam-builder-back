package com.example.exambuilder.entity.Enum;

public enum Difficulty {
    FACIL(1),
    MEDIO(2),
    DIFICIL(3);

    private final int valor;

    Difficulty(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}