package com.example.exambuilder.web.controller;

import com.example.exambuilder.entity.Question;
import com.example.exambuilder.service.QuestionService;
import com.example.exambuilder.web.dto.QuestionCreateDto;
import com.example.exambuilder.web.dto.QuestionResponseDto;
import com.example.exambuilder.web.dto.mapper.QuestionMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/questions")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<QuestionResponseDto> createQuestion(@RequestBody @Valid QuestionCreateDto dto) {
        QuestionResponseDto responseDto = questionService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponseDto> updateQuestion(@PathVariable Long id, @RequestBody @Valid QuestionCreateDto dto) {
        return ResponseEntity.ok(questionService.update(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponseDto>> getAllQuestion() {
        List<Question> questions = questionService.findAll();
        return ResponseEntity.ok(QuestionMapper.toListDto(questions));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponseDto> getByIdQuestion(@PathVariable Long id) {
        Question question = questionService.findById(id);
        return ResponseEntity.ok(QuestionMapper.toDto(question));
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<List<QuestionResponseDto>> getQuestionsByTeacher(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.findQuestionsByTeacher(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}