package com.example.exambuilder.web.dto.mapper;

import com.example.exambuilder.entity.Question;
import com.example.exambuilder.entity.Teacher;
import com.example.exambuilder.web.dto.QuestionCreateDto;
import com.example.exambuilder.web.dto.QuestionResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionMapper {

    public static Question toQuestion(QuestionCreateDto createDto, Teacher teacher) {
        Question question = new Question();

        question.setDifficulty(createDto.getDifficulty());
        question.setDiscipline(createDto.getDiscipline());
        question.setSubject(createDto.getSubject());
        question.setStatement(createDto.getStatement());
        question.setType(createDto.getType());
        question.setTeacher(teacher);

        return question;
    }

    public static QuestionResponseDto toDto(Question question) {
        ModelMapper mapper = new ModelMapper();

        PropertyMap<Question, QuestionResponseDto> props =
                new PropertyMap<Question, QuestionResponseDto>() {
                    @Override
                    protected void configure() {
                        map().setTeacherId(source.getTeacher().getId());
                        map().setTeacherName(source.getTeacher().getName());
                    }
                };

        mapper.addMappings(props);

        return mapper.map(question, QuestionResponseDto.class);
    }

    public static List<QuestionResponseDto> toListDto(List<Question> questions) {
        return questions.stream()
                .map(QuestionMapper::toDto)
                .collect(Collectors.toList());
    }
}