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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EvaluationServiceTest {

    @Mock
    private EvaluationRepository evaluationRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private EvaluationService evaluationService;

    @Test
    void shouldCreateEvaluationSuccessfully() {

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("Professor");

        Question question = new Question();
        question.setId(1L);

        EvaluationCreateDto dto = new EvaluationCreateDto();
        dto.setDiscipline("Matemática");
        dto.setGradeLevel("9º Ano");
        dto.setInstitution("Escola XPTO");
        dto.setTeacherId(1L);
        dto.setQuestionIds(List.of(1L));

        when(teacherRepository.findById(1L))
                .thenReturn(Optional.of(teacher));

        when(questionRepository.findAllById(List.of(1L)))
                .thenReturn(List.of(question));

        EvaluationResponseDto response =
                evaluationService.create(dto);

        assertNotNull(response);
        assertEquals("Matemática", response.getDiscipline());

        verify(evaluationRepository, times(1))
                .save(any(Evaluation.class));
    }

    @Test
    void shouldUpdateEvaluationSuccessfully() {

        Teacher teacher = new Teacher();
        teacher.setId(1L);

        Question question = new Question();
        question.setId(1L);

        Evaluation evaluation = new Evaluation();
        evaluation.setId(1L);

        EvaluationCreateDto dto = new EvaluationCreateDto();
        dto.setDiscipline("Português");
        dto.setGradeLevel("1º Ano");
        dto.setInstitution("Nova Escola");
        dto.setTeacherId(1L);
        dto.setQuestionIds(List.of(1L));

        when(evaluationRepository.findById(1L))
                .thenReturn(Optional.of(evaluation));

        when(teacherRepository.findById(1L))
                .thenReturn(Optional.of(teacher));

        when(questionRepository.findAllById(List.of(1L)))
                .thenReturn(List.of(question));

        EvaluationResponseDto response =
                evaluationService.update(1L, dto);

        assertNotNull(response);
        assertEquals("Português", response.getDiscipline());

        verify(evaluationRepository, times(1))
                .save(evaluation);
    }

    @Test
    void shouldReturnAllEvaluations() {

        Evaluation evaluation = new Evaluation();
        evaluation.setId(1L);

        when(evaluationRepository.findAll())
                .thenReturn(List.of(evaluation));

        List<EvaluationResponseDto> result =
                evaluationService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void shouldReturnEvaluationById() {

        Evaluation evaluation = new Evaluation();
        evaluation.setId(1L);

        when(evaluationRepository.findById(1L))
                .thenReturn(Optional.of(evaluation));

        EvaluationResponseDto response =
                evaluationService.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void shouldDeleteEvaluationSuccessfully() {

        Evaluation evaluation = new Evaluation();
        evaluation.setId(1L);

        when(evaluationRepository.findById(1L))
                .thenReturn(Optional.of(evaluation));

        evaluationService.delete(1L);

        verify(evaluationRepository, times(1))
                .delete(evaluation);
    }

    @Test
    void shouldThrowExceptionWhenEvaluationNotFound() {

        when(evaluationRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(EvaluationNotFoundException.class,
                () -> evaluationService.findById(1L));
    }

    @Test
    void shouldThrowExceptionWhenTeacherNotFound() {

        EvaluationCreateDto dto = new EvaluationCreateDto();
        dto.setTeacherId(1L);

        when(teacherRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(TeacherNotFoundException.class,
                () -> evaluationService.create(dto));
    }
}