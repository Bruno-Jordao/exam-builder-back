package com.example.exambuilder.web.exception;

import com.example.exambuilder.exceptions.EmailAlreadyExistsException;
import com.example.exambuilder.exceptions.TeacherNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TeacherNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleTeacherNotFound(
            TeacherNotFoundException ex,
            HttpServletRequest request) {

        ErrorMessage error = new ErrorMessage(
                request,
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleEmailExists(
            EmailAlreadyExistsException ex,
            HttpServletRequest request) {

        ErrorMessage error = new ErrorMessage(
                request,
                HttpStatus.CONFLICT,
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        BindingResult result = ex.getBindingResult();

        ErrorMessage error = new ErrorMessage(
                request,
                HttpStatus.BAD_REQUEST,
                "Invalid fields",
                result
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
