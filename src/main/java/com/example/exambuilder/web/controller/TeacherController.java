package com.example.exambuilder.web.controller;

import com.example.exambuilder.entity.Teacher;
import com.example.exambuilder.service.TeacherService;
import com.example.exambuilder.web.dto.TeacherCreateDto;
import com.example.exambuilder.web.dto.TeacherResponseDto;
import com.example.exambuilder.web.dto.mapper.TeacherMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    public ResponseEntity<TeacherResponseDto> createTeacher(@Valid @RequestBody TeacherCreateDto dto){
        Teacher newTeacher = teacherService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(TeacherMapper.toDto(newTeacher));
    }
}
