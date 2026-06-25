package com.example.exambuilder.web.controller;

import com.example.exambuilder.service.EvaluationService;
import com.example.exambuilder.web.dto.EvaluationCreateDto;
import com.example.exambuilder.web.dto.EvaluationResponseDto;
import com.example.exambuilder.web.dto.QuestionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping("/api/v1/evaluations")
public class EvaluationController {

    private final EvaluationService evaluationService;

    @Operation(
            summary = "Create a new evaluation",
            description = "Registers a new evaluation in the system associated with a teacher",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Evaluation successfully created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EvaluationResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request data"),
                    @ApiResponse(responseCode = "404", description = "Teacher not found")
            }
    )

    @PostMapping
    public ResponseEntity<EvaluationResponseDto> createEvaluation(@RequestBody @Valid EvaluationCreateDto dto) {
        EvaluationResponseDto responseDto = evaluationService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(
            summary = "Update evaluation",
            description = "Updates evaluation information based on ID",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evaluation successfully updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EvaluationResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request data"),
                    @ApiResponse(responseCode = "404", description = "Evaluation not found"),
                    @ApiResponse(responseCode = "404", description = "Teacher not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )

    @PutMapping("/{id}")
    public ResponseEntity<EvaluationResponseDto> updateEvaluation(@PathVariable Long id, @RequestBody @Valid EvaluationCreateDto dto) {
        EvaluationResponseDto responseDto = evaluationService.update(id, dto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(
            summary = "List all evaluations",
            description = "Returns a list of all registered evaluations",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of evaluations",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = EvaluationResponseDto.class)))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )

    @GetMapping
    public ResponseEntity<List<EvaluationResponseDto>> getAllEvaluation() {
        return ResponseEntity.ok(evaluationService.findAll());
    }

    @Operation(
            summary = "Find evaluation by ID",
            description = "Returns a evaluation based on the provided ID",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evaluation found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EvaluationResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Evaluation not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )

    @GetMapping("/{id}")
    public ResponseEntity<EvaluationResponseDto> getByIdEvaluation(@PathVariable Long id) {
        return ResponseEntity.ok(evaluationService.findById(id));
    }

    @Operation(
            summary = "Delete evaluation",
            description = "Deletes a evaluation from the system based on ID",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Evaluation successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "Evaluation not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long id) {
        evaluationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Download evaluation as PDF",
            description = "Generates and downloads the evaluation in PDF format",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "PDF generated successfully"),
                    @ApiResponse(responseCode = "404", description = "Evaluation not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {

        byte[] pdf = evaluationService.generatePdf(id);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=evaluation-" + id + ".pdf"
                )
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdf.length)
                .body(pdf);
    }
}