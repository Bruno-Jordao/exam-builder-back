package com.example.exambuilder.repository;

import com.example.exambuilder.entity.QuestionAlternative;
import org.springframework.data.jpa.repository.JpaRepository;
 
import java.util.List;
 
public interface QuestionAlternativeRepository extends JpaRepository<QuestionAlternative, Long> {
 
    List<QuestionAlternative> findByQuestionId(Long questionId);
}
 