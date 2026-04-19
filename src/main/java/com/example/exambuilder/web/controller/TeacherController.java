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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<TeacherResponseDto>> getAllTeachers(){
        List<Teacher> teachers = teacherService.findAll();
        return ResponseEntity.ok(TeacherMapper.toListDto(teachers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> getTeacherById(@PathVariable Long id){
        Teacher teacher = teacherService.findById(id);
        return ResponseEntity.ok(TeacherMapper.toDto(teacher));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> updateTeacher(
            @PathVariable Long id,
            @Valid @RequestBody TeacherCreateDto dto){
        Teacher updatedTeacher = teacherService.update(id, dto);
        return ResponseEntity.ok(TeacherMapper.toDto(updatedTeacher));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id){
        teacherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
