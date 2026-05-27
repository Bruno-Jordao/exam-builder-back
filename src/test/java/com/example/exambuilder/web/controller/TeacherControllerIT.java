package com.example.exambuilder.web.controller;

import com.example.exambuilder.entity.Teacher;
import com.example.exambuilder.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TeacherControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherRepository repository;

    private String getToken() throws Exception {

        String createBody = """
        {
          "name": "Bruno",
          "email": "bruno@email.com",
          "password": "123456"
        }
        """;

        mockMvc.perform(post("/api/v1/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createBody));

        String authBody = """
        {
          "email": "bruno@email.com",
          "password": "123456"
        }
        """;

        String response = mockMvc.perform(post("/api/v1/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authBody))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> json = mapper.readValue(response, Map.class);

        return json.get("token");
    }

    @Test
    void shouldCreateTeacherSuccessfully() throws Exception {

        String body = """
        {
          "name": "Bruno",
          "email": "bruno@email.com",
          "password": "123456"
        }
        """;

        mockMvc.perform(post("/api/v1/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("bruno@email.com"));
    }

    @Test
    void shouldReturnTeacherByIdAuthenticated() throws Exception {

        String token = getToken();

        Teacher teacher = repository.findByEmail("bruno@email.com")
                .orElseThrow();

        mockMvc.perform(get("/api/v1/teachers/" + teacher.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("bruno@email.com"));
    }
}