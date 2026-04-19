package com.example.exambuilder.service;

import com.example.exambuilder.entity.Teacher;
import com.example.exambuilder.exceptions.EmailAlreadyExistsException;
import com.example.exambuilder.exceptions.TeacherNotFoundException;
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

        if (teacherRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException("This email is already in use: " + dto.getEmail());
        }

        Teacher teacher = TeacherMapper.toTeacher(dto);
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        return teacherRepository.save(teacher);
    }

    @Transactional(readOnly = true)
    public Teacher searchByEmail(String email){
        return teacherRepository.findByEmail(email)
                .orElseThrow(() ->
                        new TeacherNotFoundException("Teacher not found with email: " + email));
    }

    @Transactional(readOnly = true)
    public java.util.List<Teacher> findAll(){
        return teacherRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Teacher findById(Long id){
        return teacherRepository.findById(id)
                .orElseThrow(() ->
                        new TeacherNotFoundException("Teacher not found with id: " + id));
    }

    @Transactional
    public Teacher update(Long id, TeacherCreateDto dto){

        Teacher teacher = findById(id);

        if (!teacher.getEmail().equals(dto.getEmail())
                && teacherRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException("This email is already in use: " + dto.getEmail());
        }

        teacher.setName(dto.getName());
        teacher.setEmail(dto.getEmail());
        teacher.setPassword(passwordEncoder.encode(dto.getPassword()));

        return teacherRepository.save(teacher);
    }

    @Transactional
    public void delete(Long id){
        Teacher teacher = findById(id);
        teacherRepository.delete(teacher);
    }
}
