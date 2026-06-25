package com.example.exambuilder.repository;

import com.example.exambuilder.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    @Query("""
        SELECT DISTINCT e
        FROM Evaluation e
        LEFT JOIN FETCH e.teacher
        LEFT JOIN FETCH e.questions q
        LEFT JOIN FETCH q.alternatives
        WHERE e.id = :id
    """)
    Optional<Evaluation> findByIdWithQuestionsAndAlternatives(
            @Param("id") Long id);
}