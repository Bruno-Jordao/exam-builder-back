package com.example.exambuilder.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    private Long teacherId;
    private String teacherName;
}