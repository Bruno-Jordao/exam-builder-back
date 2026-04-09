package com.example.exambuilder.web.dto.mapper;

import com.example.exambuilder.entity.Evaluation;
import com.example.exambuilder.web.dto.EvaluationCreateDto;
import com.example.exambuilder.web.dto.EvaluationResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class EvaluationMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Evaluation toEvaluation(EvaluationCreateDto createDto){
        return modelMapper.map(createDto, Evaluation.class);
    }

    public static EvaluationResponseDto toDto(Evaluation evaluation){
        return modelMapper.map(evaluation, EvaluationResponseDto.class);
    }

    public static List<EvaluationResponseDto> toListDto(List<Evaluation> evaluations){
        return evaluations.stream()
                .map(EvaluationMapper::toDto)
                .collect(Collectors.toList());
    }
}