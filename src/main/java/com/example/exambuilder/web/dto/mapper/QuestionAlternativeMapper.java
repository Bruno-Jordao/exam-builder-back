package com.example.exambuilder.web.dto.mapper;
 
import com.example.exambuilder.entity.QuestionAlternative;
import com.example.exambuilder.web.dto.QuestionAlternativeCreateDto;
import com.example.exambuilder.web.dto.QuestionAlternativeResponseDto;
import org.modelmapper.ModelMapper;
 
import java.util.List;
import java.util.stream.Collectors;

public class QuestionAlternativeMapper {

    public static QuestionAlternative toQuestionAlternative(
            QuestionAlternativeCreateDto createDto) {

        QuestionAlternative alternative = new QuestionAlternative();

        alternative.setText(createDto.getText());

        return alternative;
    }

    public static QuestionAlternativeResponseDto toDto(
            QuestionAlternative alternative) {

        QuestionAlternativeResponseDto dto =
                new QuestionAlternativeResponseDto();

        dto.setId(alternative.getId());
        dto.setText(alternative.getText());

        return dto;
    }

    public static List<QuestionAlternativeResponseDto> toListDto(
            List<QuestionAlternative> alternatives) {

        return alternatives.stream()
                .map(QuestionAlternativeMapper::toDto)
                .collect(Collectors.toList());
    }
}