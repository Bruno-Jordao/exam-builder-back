package com.example.exambuilder.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuestionCreateDto {

    @NotNull
    private Difficulty difficulty;

    @NotBlank
    private String discipline;

    @NotBlank
    private String subject;

    @NotBlank
    private String statement;

    @NotNull
    private QuestionType type;
}