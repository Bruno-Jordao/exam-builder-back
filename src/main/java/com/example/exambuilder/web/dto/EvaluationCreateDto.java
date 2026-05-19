package com.example.exambuilder.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

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

    @NotNull
    private Long teacherId;

    @NotEmpty
    private List<Long> questionIds;
}