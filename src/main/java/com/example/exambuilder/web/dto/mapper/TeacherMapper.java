package com.example.exambuilder.web.dto.mapper;

import com.example.exambuilder.entity.Teacher;
import com.example.exambuilder.web.dto.TeacherCreateDto;
import com.example.exambuilder.web.dto.TeacherResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class TeacherMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Teacher toTeacher(TeacherCreateDto createDto){
        return modelMapper.map(createDto, Teacher.class);
    }

    public static TeacherResponseDto toDto(Teacher teacher){
        return modelMapper.map(teacher, TeacherResponseDto.class);
    }

    public static List<TeacherResponseDto> toListDto(List<Teacher> allUsers){
        return allUsers.stream().map(teacher -> toDto(teacher)).collect(Collectors.toList());
    }
}
