package com.example.exambuilder.service;

import com.example.exambuilder.entity.Enum.Difficulty;
import com.example.exambuilder.entity.Enum.QuestionType;
import com.example.exambuilder.entity.Question;
import com.example.exambuilder.entity.Teacher;
import com.example.exambuilder.service.TeacherService;
import com.example.exambuilder.service.QuestionService;
import com.example.exambuilder.exceptions.QuestionNotFoundException;
import com.example.exambuilder.exceptions.TeacherNotFoundException;
import com.example.exambuilder.repository.QuestionRepository;
import com.example.exambuilder.repository.TeacherRepository;
import com.example.exambuilder.web.dto.QuestionCreateDto;
import com.example.exambuilder.web.dto.QuestionResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private QuestionService questionService;

    @Test
    void shouldCreateQuestionSuccessfully() {

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("Professor");

        QuestionCreateDto dto = new QuestionCreateDto();
        dto.setDifficulty(Difficulty.FACIL);
        dto.setDiscipline("Matemática");
        dto.setSubject("Soma");
        dto.setStatement("Quanto é 2 + 2?");
        dto.setType(QuestionType.FECHADA);
        dto.setTeacherId(1L);

        when(teacherRepository.findById(1L))
                .thenReturn(Optional.of(teacher));

        QuestionResponseDto response = questionService.create(dto);

        assertNotNull(response);
        assertEquals("Matemática", response.getDiscipline());

        verify(questionRepository, times(1))
                .save(any(Question.class));
    }

    @Test
    void shouldThrowExceptionWhenTeacherNotFound() {

        QuestionCreateDto dto = new QuestionCreateDto();
        dto.setTeacherId(1L);

        when(teacherRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(com.example.exambuilder.exceptions.TeacherNotFoundException.class,
                () -> questionService.create(dto));
    }

    @Test
    void shouldFindQuestionById() {

        Question question = new Question();

        question.setId(1L);
        question.setStatement("Pergunta teste");

        when(questionRepository.findById(1L))
                .thenReturn(Optional.of(question));

        Question result = questionService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowExceptionWhenQuestionNotFound() {

        when(questionRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(com.example.exambuilder.exceptions.QuestionNotFoundException.class,
                () -> questionService.findById(1L));
    }

    @Test
    void shouldReturnAllQuestions() {

        Question q1 = new Question();
        Question q2 = new Question();

        when(questionRepository.findAll())
                .thenReturn(List.of(q1, q2));

        List<Question> questions = questionService.findAll();

        assertEquals(2, questions.size());
    }

    @Test
    void shouldDeleteQuestionSuccessfully() {

        Question question = new Question();
        question.setId(1L);

        when(questionRepository.findById(1L))
                .thenReturn(Optional.of(question));

        questionService.delete(1L);

        verify(questionRepository, times(1))
                .delete(question);
    }

    @Test
    void shouldUpdateQuestionSuccessfully() {

        Question question = new Question();

        question.setId(1L);
        question.setCreatedAt(LocalDateTime.now());

        QuestionCreateDto dto = new QuestionCreateDto();

        dto.setDifficulty(Difficulty.MEDIO);
        dto.setDiscipline("Português");
        dto.setSubject("Gramática");
        dto.setStatement("Nova pergunta");
        dto.setType(QuestionType.ABERTA);

        when(questionRepository.findById(1L))
                .thenReturn(Optional.of(question));

        QuestionResponseDto response = questionService.update(1L, dto);

        assertNotNull(response);
        assertEquals("Português", response.getDiscipline());
        assertEquals("Gramática", response.getSubject());

        verify(questionRepository, times(1))
                .save(question);
    }

    @Test
    void shouldReturnQuestionsByTeacher() {

        Teacher teacher = new Teacher();
        teacher.setId(1L);

        Question question = new Question();
        question.setId(1L);

        when(questionRepository.findByTeacherId(1L))
                .thenReturn(List.of(question));

        when(teacherService.findById(1L))
                .thenReturn(teacher);

        List<QuestionResponseDto> result =
                questionService.findQuestionsByTeacher(1L);

        assertEquals(1, result.size());
    }
}