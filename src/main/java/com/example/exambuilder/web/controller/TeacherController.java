package com.example.exambuilder.web.controller;

import com.example.exambuilder.entity.Teacher;
import com.example.exambuilder.service.TeacherService;
import com.example.exambuilder.web.dto.TeacherCreateDto;
import com.example.exambuilder.web.dto.TeacherResponseDto;
import com.example.exambuilder.web.dto.mapper.TeacherMapper;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @Operation(
            summary = "Create a new teacher",
            description = "Registers a new teacher in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Teacher successfully created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TeacherResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request data"),
                    @ApiResponse(responseCode = "409", description = "Email already exists")
            }
    )
    @PostMapping
    public ResponseEntity<TeacherResponseDto> createTeacher(@Valid @RequestBody TeacherCreateDto dto){
        Teacher newTeacher = teacherService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(TeacherMapper.toDto(newTeacher));
    }


    @Operation(summary = "List all teachers", description = "List of all registered teachers",
    security = @SecurityRequirement(name = "security"),
    responses = {
            @ApiResponse(responseCode = "200", description = "List of all registered teachers",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = TeacherResponseDto.class))))
    })
    @GetMapping
    public ResponseEntity<List<TeacherResponseDto>> getAllTeachers(){
        List<Teacher> teachers = teacherService.findAll();
        return ResponseEntity.ok(TeacherMapper.toListDto(teachers));
    }

    @Operation(
            summary = "Find teacher by ID",
            description = "Returns a teacher based on the provided ID",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Teacher found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TeacherResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Teacher not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> getTeacherById(@PathVariable Long id){
        Teacher teacher = teacherService.findById(id);
        return ResponseEntity.ok(TeacherMapper.toDto(teacher));
    }

    @Operation(
            summary = "Update teacher",
            description = "Updates teacher information based on ID",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Teacher successfully updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TeacherResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request data"),
                    @ApiResponse(responseCode = "404", description = "Teacher not found"),
                    @ApiResponse(responseCode = "409", description = "Email already exists"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> updateTeacher(
            @PathVariable Long id,
            @Valid @RequestBody TeacherCreateDto dto){
        Teacher updatedTeacher = teacherService.update(id, dto);
        return ResponseEntity.ok(TeacherMapper.toDto(updatedTeacher));
    }

    @Operation(
            summary = "Delete teacher",
            description = "Deletes a teacher from the system based on ID",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Teacher successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "Teacher not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id){
        teacherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
