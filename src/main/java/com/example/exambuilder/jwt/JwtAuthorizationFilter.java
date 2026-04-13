package com.example.exambuilder.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUserDetailsService detailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String token = request.getHeader(JwtUtils.JWT_AUTHORIZATION);

        if(token == null || !token.startsWith(JwtUtils.JWT_BEARER)){
            log.info("JWT token is null, empty, or not initialized with 'Bearer '.");
            filterChain.doFilter(request, response);
            return;
        }

        if(!JwtUtils.isTokenValid(token)){
            log.warn("The JWT token is invalid or expired.");
            filterChain.doFilter(request, response);
            return;
        }

        String email = JwtUtils.getUsernameFromToken(token);

        toAuthentication(request, email);

        filterChain.doFilter(request, response);
    }

    private void toAuthentication(HttpServletRequest request, String email) {
        UserDetails userDetails = detailsService.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
                .authenticated(userDetails, null, userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/api/v1/auth");
    }
}
