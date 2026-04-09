package com.example.exambuilder.web.dto;

import jakarta.validation.constraints.NotBlank;
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
public class EvaluationCreateDto {

    @NotBlank
    private String discipline;

    @NotBlank
    private String gradeLevel;

    @NotBlank
    private String institution;
}