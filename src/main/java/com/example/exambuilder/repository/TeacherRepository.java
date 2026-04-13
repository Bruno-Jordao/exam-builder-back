package com.example.exambuilder.repository;

import com.example.exambuilder.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByEmail(String email);

    boolean existsByEmailIgnoreCase(String email);
}
