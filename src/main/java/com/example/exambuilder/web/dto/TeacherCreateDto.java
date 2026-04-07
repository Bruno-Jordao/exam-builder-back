package com.example.exambuilder.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeacherCreateDto {

    @NotBlank
    private String name;

    @NotBlank
    @Email(message = "Invalid Format")
    private String email;

    @NotBlank
    @Size(min = 6, max = 6, message = "The password must be 6 characters long.")
    private String password;
}
