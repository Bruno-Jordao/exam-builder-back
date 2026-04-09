package com.example.exambuilder.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EvaluationResponseDto {

    private Long id;
    private String discipline;
    private String gradeLevel;
    private String institution;
    private LocalDateTime createdAt;
}