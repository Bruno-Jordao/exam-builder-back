package com.example.exambuilder.web.dto.mapper;

import com.example.exambuilder.entity.QuestionAlternative;
import com.example.exambuilder.web.dto.QuestionAlternativeCreateDto;
import com.example.exambuilder.web.dto.QuestionAlternativeResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionAlternativeMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static QuestionAlternative toQuestionAlternative(QuestionAlternativeCreateDto createDto) {
        return modelMapper.map(createDto, QuestionAlternative.class);
    }

    public static QuestionAlternativeResponseDto toDto(QuestionAlternative alternative) {
        return modelMapper.map(alternative, QuestionAlternativeResponseDto.class);
    }

    public static List<QuestionAlternativeResponseDto> toListDto(List<QuestionAlternative> alternatives) {
        return alternatives.stream()
                .map(QuestionAlternativeMapper::toDto)
                .collect(Collectors.toList());
    }
}