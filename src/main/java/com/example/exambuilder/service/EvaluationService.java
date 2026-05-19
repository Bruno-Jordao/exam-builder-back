package com.example.exambuilder.service;

import com.example.exambuilder.entity.Evaluation;
import com.example.exambuilder.entity.Question;
import com.example.exambuilder.entity.Teacher;
import com.example.exambuilder.exceptions.EvaluationNotFoundException;
import com.example.exambuilder.exceptions.TeacherNotFoundException;
import com.example.exambuilder.repository.EvaluationRepository;
import com.example.exambuilder.repository.QuestionRepository;
import com.example.exambuilder.repository.TeacherRepository;
import com.example.exambuilder.web.dto.EvaluationCreateDto;
import com.example.exambuilder.web.dto.EvaluationResponseDto;
import com.example.exambuilder.web.dto.mapper.EvaluationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final TeacherRepository teacherRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public EvaluationResponseDto create(EvaluationCreateDto createDto) {
        Teacher teacher = teacherRepository.findById(createDto.getTeacherId()).orElseThrow(
                () -> new TeacherNotFoundException("Teacher not found!")
        );

        List<Question> questions = questionRepository.findAllById(createDto.getQuestionIds());

        Evaluation evaluation = new Evaluation();

        evaluation.setDiscipline(createDto.getDiscipline());
        evaluation.setCreatedAt(LocalDateTime.now());
        evaluation.setGradeLevel(createDto.getGradeLevel());
        evaluation.setInstitution(createDto.getInstitution());
        evaluation.setTeacher(teacher);
        evaluation.setQuestions(questions);

        evaluationRepository.save(evaluation);

        return EvaluationMapper.toDto(evaluation);
    }

    @Transactional
    public EvaluationResponseDto update(Long id, EvaluationCreateDto createDto) {
        Evaluation evaluation = evaluationRepository.findById(id).orElseThrow(
                () -> new EvaluationNotFoundException("Evaluation not found!")
        );
        Teacher teacher = teacherRepository.findById(createDto.getTeacherId()).orElseThrow(
                () -> new TeacherNotFoundException("Teacher not found!")
        );

        List<Question> questions = questionRepository.findAllById(createDto.getQuestionIds());

        evaluation.setDiscipline(createDto.getDiscipline());
        evaluation.setGradeLevel(createDto.getGradeLevel());
        evaluation.setInstitution(createDto.getInstitution());
        evaluation.setTeacher(teacher);
        evaluation.setQuestions(questions);

        evaluationRepository.save(evaluation);

        return EvaluationMapper.toDto(evaluation);
    }

    @Transactional
    public List<EvaluationResponseDto> findAll() {
        return EvaluationMapper.toListDto(evaluationRepository.findAll());
    }

    @Transactional
    public EvaluationResponseDto findById(Long id) {
        Evaluation evaluation = evaluationRepository.findById(id).orElseThrow(
                () -> new EvaluationNotFoundException("Evaluation not found!")
        );

        return EvaluationMapper.toDto(evaluation);
    }

    @Transactional
    public void delete(Long id) {
        Evaluation evaluation = evaluationRepository.findById(id).orElseThrow(
                () -> new EvaluationNotFoundException("Evaluation not found!")
        );
        evaluationRepository.delete(evaluation);
    }
}