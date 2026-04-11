package com.example.exambuilder.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import com.example.exambuilder.entity.Enum.Difficulty;
import com.example.exambuilder.entity.Enum.QuestionType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuestionResponseDto {

    private Long id;
    private Difficulty difficulty;
    private String discipline;
    private String subject;
    private String statement;
    private QuestionType type;
    private LocalDateTime createdAt;
}