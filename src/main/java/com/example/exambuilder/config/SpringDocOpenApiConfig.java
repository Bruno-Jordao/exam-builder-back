package com.example.exambuilder.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(
                        new Components()
                                .addSecuritySchemes("security", securityScheme())
                )
                .addSecurityItem(
                        new SecurityRequirement().addList("security")
                )
                .info(
                        new Info()
                                .title("REST API - Spring ExamBuilder")
                                .description("API for managing question banks and exams")
                                .version("v1")
                                .contact(
                                        new Contact()
                                                .name("Exambuilder Development Team")
                                )
                );
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .description("Enter only the JWT token");
    }
}