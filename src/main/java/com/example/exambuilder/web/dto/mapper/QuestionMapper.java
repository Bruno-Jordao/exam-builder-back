package com.example.exambuilder.web.dto.mapper;

import com.example.exambuilder.entity.Question;
import com.example.exambuilder.web.dto.QuestionCreateDto;
import com.example.exambuilder.web.dto.QuestionResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Question toQuestion(QuestionCreateDto createDto){
        return modelMapper.map(createDto, Question.class);
    }

    public static QuestionResponseDto toDto(Question question){
        return modelMapper.map(question, QuestionResponseDto.class);
    }

    public static List<QuestionResponseDto> toListDto(List<Question> questions){
        return questions.stream()
                .map(QuestionMapper::toDto)
                .collect(Collectors.toList());
    }
}