package com.example.exambuilder.service;

import com.example.exambuilder.entity.Question;
import com.example.exambuilder.entity.Teacher;
import com.example.exambuilder.exceptions.QuestionNotFoundException;
import com.example.exambuilder.exceptions.TeacherNotFoundException;
import com.example.exambuilder.repository.QuestionRepository;
import com.example.exambuilder.repository.TeacherRepository;
import com.example.exambuilder.web.dto.QuestionCreateDto;
import com.example.exambuilder.web.dto.QuestionResponseDto;
import com.example.exambuilder.web.dto.mapper.QuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final TeacherRepository teacherRepository;
    private final TeacherService teacherService;

    @Transactional
    public QuestionResponseDto create(QuestionCreateDto createDto) {
        Teacher teacher = teacherRepository.findById(createDto.getTeacherId()).orElseThrow(
                () -> new TeacherNotFoundException("Teacher not found!")
        );

        Question question = QuestionMapper.toQuestion(createDto, teacher);
        question.setCreatedAt(LocalDateTime.now());
        question.setTeacher(teacher);

        questionRepository.save(question);

        return QuestionMapper.toDto(question);
    }

    @Transactional
    public QuestionResponseDto update(Long id, QuestionCreateDto createDto) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new QuestionNotFoundException("Question not found!")
        );

        question.setDifficulty(createDto.getDifficulty());
        question.setDiscipline(createDto.getDiscipline());
        question.setSubject(createDto.getSubject());
        question.setStatement(createDto.getStatement());
        question.setType(createDto.getType());

        questionRepository.save(question);

        return QuestionMapper.toDto(question);
    }

    @Transactional
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(
                () -> new QuestionNotFoundException("Question not found!")
        );
    }

    @Transactional(readOnly = true)
    public List<QuestionResponseDto> findQuestionsByTeacher(Long id) {
        teacherService.findById(id);
        List<Question> questions = questionRepository.findByTeacherId(id);
        return QuestionMapper.toListDto(questions);
    }

    @Transactional
    public void delete(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new QuestionNotFoundException("Question not found!")
        );
        questionRepository.delete(question);
    }
}