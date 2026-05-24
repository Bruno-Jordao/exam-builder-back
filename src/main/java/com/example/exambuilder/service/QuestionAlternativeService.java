package com.example.exambuilder.service;

import com.example.exambuilder.entity.Question;
import com.example.exambuilder.entity.QuestionAlternative;
import com.example.exambuilder.web.exception.*;
import com.example.exambuilder.exceptions.QuestionNotFoundException;
import com.example.exambuilder.repository.QuestionAlternativeRepository;
import com.example.exambuilder.repository.QuestionRepository;
import com.example.exambuilder.web.dto.QuestionAlternativeCreateDto;
import com.example.exambuilder.web.dto.QuestionAlternativeResponseDto;
import com.example.exambuilder.web.dto.mapper.QuestionAlternativeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionAlternativeService {

    private final QuestionAlternativeRepository questionAlternativeRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public QuestionAlternativeResponseDto create(QuestionAlternativeCreateDto createDto) {
        Question question = questionRepository.findById(createDto.getQuestionId()).orElseThrow(
                () -> new QuestionNotFoundException("Question not found!")
        );

        QuestionAlternative questionAlternative = QuestionAlternativeMapper.toQuestionAlternative(createDto);
        questionAlternative.setQuestion(question);

        questionAlternativeRepository.save(questionAlternative);

        return QuestionAlternativeMapper.toDto(questionAlternative);
    }

    @Transactional
    public QuestionAlternativeResponseDto update(Long id, QuestionAlternativeCreateDto createDto) {
        QuestionAlternative questionAlternative = questionAlternativeRepository.findById(id).orElseThrow(
                () -> new QuestionAlternativeNotFoundException("Question alternative not found!")
        );

        questionAlternative.setText(createDto.getText());

        questionAlternativeRepository.save(questionAlternative);

        return QuestionAlternativeMapper.toDto(questionAlternative);
    }

    @Transactional(readOnly = true)
    public List<QuestionAlternativeResponseDto> findAll() {
        return QuestionAlternativeMapper.toListDto(questionAlternativeRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<QuestionAlternativeResponseDto> findByQuestionId(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new QuestionNotFoundException("Question not found!");
        }
        return QuestionAlternativeMapper.toListDto(questionAlternativeRepository.findByQuestionId(questionId));
    }

    @Transactional(readOnly = true)
    public QuestionAlternativeResponseDto findById(Long id) {
        QuestionAlternative questionAlternative = questionAlternativeRepository.findById(id).orElseThrow(
                () -> new QuestionAlternativeNotFoundException("Question alternative not found!")
        );
        return QuestionAlternativeMapper.toDto(questionAlternative);
    }

    @Transactional
    public void delete(Long id) {
        QuestionAlternative questionAlternative = questionAlternativeRepository.findById(id).orElseThrow(
                () -> new QuestionAlternativeNotFoundException("Question alternative not found!")
        );
        questionAlternativeRepository.delete(questionAlternative);
    }
}