package com.example.exambuilder.web.controller;

import com.example.exambuilder.jwt.JwtToken;
import com.example.exambuilder.jwt.JwtUserDetailsService;
import com.example.exambuilder.web.dto.UserLoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody @Valid UserLoginDto dto, HttpServletRequest request){
        log.info("Authentication process via login {}", dto.getEmail());
        try{
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
            authenticationManager.authenticate(authenticationToken);
            JwtToken token = detailsService.getTokenAuthenticated(dto.getEmail());
            return ResponseEntity.ok(token);
        }catch(org.springframework.security.core.AuthenticationException ex){
            log.warn("Bad credentials from email '{}'", dto.getEmail());
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Invalid credentials!");
    }
}
