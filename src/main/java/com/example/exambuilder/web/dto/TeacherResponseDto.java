package com.example.exambuilder.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeacherResponseDto {

    private Long id;
    private String email;
    private String name;
}
