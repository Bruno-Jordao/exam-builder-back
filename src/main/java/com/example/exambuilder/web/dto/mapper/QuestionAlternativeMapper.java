package com.example.exambuilder.web.dto.mapper;

import com.example.exambuilder.entity.QuestionAlternative;
import com.example.exambuilder.web.dto.QuestionAlternativeDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionAlternativeMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static QuestionAlternative toQuestionAlternative(QuestionAlternativeDto createDto) {
        return modelMapper.map(createDto, QuestionAlternative.class);
    }

    public static QuestionAlternativeDto toDto(QuestionAlternative alternative) {
        return modelMapper.map(alternative, QuestionAlternativeDto.class);
    }

    public static List<QuestionAlternativeDto> toListDto(List<QuestionAlternative> alternatives) {
        return alternatives.stream()
                .map(QuestionAlternativeMapper::toDto)
                .collect(Collectors.toList());
    }
}