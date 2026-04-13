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
public class UserLoginDto {

    @NotBlank
    @Email(message = "Invalid format")
    private String email;

    @NotBlank
    @Size(min = 6, max = 6, message = "The password must be 6 characters long.")
    private String password;
}
