package com.example.exambuilder.web.controller;

import com.example.exambuilder.service.QuestionAlternativeService;
import com.example.exambuilder.web.dto.QuestionAlternativeCreateDto;
import com.example.exambuilder.web.dto.QuestionAlternativeResponseDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/question-alternatives")
public class QuestionAlternativeController {

    private final QuestionAlternativeService questionAlternativeService;

    @Operation(
            summary = "Create a new question alternative",
            description = "Registers a new alternative associated with a question",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Alternative successfully created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = QuestionAlternativeResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request data"),
                    @ApiResponse(responseCode = "404", description = "Question not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PostMapping
    public ResponseEntity<QuestionAlternativeResponseDto> createQuestionAlternative(
            @RequestBody @Valid QuestionAlternativeCreateDto dto) {
        QuestionAlternativeResponseDto responseDto = questionAlternativeService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(
            summary = "Update question alternative",
            description = "Updates question alternative information based on ID",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Alternative successfully updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = QuestionAlternativeResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request data"),
                    @ApiResponse(responseCode = "404", description = "Alternative not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<QuestionAlternativeResponseDto> updateQuestionAlternative(
            @PathVariable Long id, @RequestBody @Valid QuestionAlternativeCreateDto dto) {
        return ResponseEntity.ok(questionAlternativeService.update(id, dto));
    }

    @Operation(
            summary = "List all question alternatives",
            description = "Returns a list of all registered question alternatives",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of question alternatives",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = QuestionAlternativeResponseDto.class)))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @GetMapping
    public ResponseEntity<List<QuestionAlternativeResponseDto>> getAllQuestionAlternatives() {
        return ResponseEntity.ok(questionAlternativeService.findAll());
    }

    @Operation(
            summary = "Find question alternative by ID",
            description = "Returns a question alternative based on the provided ID",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Alternative found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = QuestionAlternativeResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Alternative not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<QuestionAlternativeResponseDto> getByIdQuestionAlternative(@PathVariable Long id) {
        return ResponseEntity.ok(questionAlternativeService.findById(id));
    }

    @Operation(
            summary = "List alternatives by question",
            description = "Returns all alternatives associated with a specific question",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of alternatives by question",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = QuestionAlternativeResponseDto.class)))),
                    @ApiResponse(responseCode = "404", description = "Question not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @GetMapping("/question/{id}")
    public ResponseEntity<List<QuestionAlternativeResponseDto>> getAlternativesByQuestion(@PathVariable Long id) {
        return ResponseEntity.ok(questionAlternativeService.findByQuestionId(id));
    }

    @Operation(
            summary = "Delete question alternative",
            description = "Deletes a question alternative from the system based on ID",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Alternative successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "Alternative not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestionAlternative(@PathVariable Long id) {
        questionAlternativeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}