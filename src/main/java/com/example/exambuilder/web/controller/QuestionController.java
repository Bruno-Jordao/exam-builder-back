package com.example.exambuilder.web.controller;

import com.example.exambuilder.entity.Question;
import com.example.exambuilder.service.QuestionService;
import com.example.exambuilder.web.dto.QuestionCreateDto;
import com.example.exambuilder.web.dto.QuestionResponseDto;
import com.example.exambuilder.web.dto.mapper.QuestionMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(
            summary = "Create a new question",
            description = "Registers a new question in the system associated with a teacher",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Question successfully created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = QuestionResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request data"),
                    @ApiResponse(responseCode = "404", description = "Teacher not found")
            }
    )
    @PostMapping
    public ResponseEntity<QuestionResponseDto> createQuestion(@RequestBody @Valid QuestionCreateDto dto) {
        QuestionResponseDto responseDto = questionService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(
            summary = "Update question",
            description = "Updates question information based on ID",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Question successfully updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = QuestionResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request data"),
                    @ApiResponse(responseCode = "404", description = "Question not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponseDto> updateQuestion(@PathVariable Long id, @RequestBody @Valid QuestionCreateDto dto) {
        return ResponseEntity.ok(questionService.update(id, dto));
    }

    @Operation(
            summary = "List all questions",
            description = "Returns a list of all registered questions",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of questions",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = QuestionResponseDto.class)))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @GetMapping
    public ResponseEntity<List<QuestionResponseDto>> getAllQuestion() {
        List<Question> questions = questionService.findAll();
        return ResponseEntity.ok(QuestionMapper.toListDto(questions));
    }

    @Operation(
            summary = "Find question by ID",
            description = "Returns a question based on the provided ID",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Question found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = QuestionResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Question not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponseDto> getByIdQuestion(@PathVariable Long id) {
        Question question = questionService.findById(id);
        return ResponseEntity.ok(QuestionMapper.toDto(question));
    }

    @Operation(
            summary = "List questions by teacher",
            description = "Returns all questions created by a specific teacher",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of questions by teacher",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = QuestionResponseDto.class)))),
                    @ApiResponse(responseCode = "404", description = "Teacher not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @GetMapping("/teacher/{id}")
    public ResponseEntity<List<QuestionResponseDto>> getQuestionsByTeacher(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.findQuestionsByTeacher(id));
    }

    @Operation(
            summary = "Delete question",
            description = "Deletes a question from the system based on ID",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Question successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "Question not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}