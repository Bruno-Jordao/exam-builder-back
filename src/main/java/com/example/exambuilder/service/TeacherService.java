package com.example.exambuilder.service;

import com.example.exambuilder.entity.Teacher;
import com.example.exambuilder.repository.TeacherRepository;
import com.example.exambuilder.web.dto.TeacherCreateDto;
import com.example.exambuilder.web.dto.mapper.TeacherMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Teacher save(TeacherCreateDto dto){
        Teacher teacher = TeacherMapper.toTeacher(dto);
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        return teacherRepository.save(teacher);
    }

    @Transactional(readOnly = true)
    public Teacher searchByEmail(String email){
        return teacherRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
    }
}
