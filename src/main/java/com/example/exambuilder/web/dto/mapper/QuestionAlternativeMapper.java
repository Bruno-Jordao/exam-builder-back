package com.example.exambuilder.web.dto.mapper;

import com.example.exambuilder.entity.QuestionAlternative;
import com.example.exambuilder.web.dto.QuestionAlternativeDto;

public class QuestionAlternativeMapper {

    public static QuestionAlternative toEntity(QuestionAlternativeDto dto) {
        QuestionAlternative alternative = new QuestionAlternative();
        alternative.setText(dto.getText());
        return alternative;
    }

    public static QuestionAlternativeDto toDto(QuestionAlternative entity) {
        return new QuestionAlternativeDto(entity.getText());
    }
}