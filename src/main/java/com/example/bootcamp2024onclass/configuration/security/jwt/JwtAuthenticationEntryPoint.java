package com.example.bootcamp2024onclass.configuration.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");

        String errorMessage = "Acceso no autorizado";
        if (request.getSession().getAttribute("error") != null) {
            errorMessage = request.getSession().getAttribute("error").toString();
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", errorMessage);

        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
