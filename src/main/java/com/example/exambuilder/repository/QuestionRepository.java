package com.example.exambuilder.repository;

import com.example.exambuilder.entity.Question;
import com.example.exambuilder.web.dto.QuestionCreateDto;
import com.example.exambuilder.web.dto.QuestionResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByTeacherId(Long id);
}