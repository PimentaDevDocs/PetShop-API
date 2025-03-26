package com.pimenta.petshop.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Exception exception = (Exception) request.getAttribute("jwtException");
        String message = exception != null ? exception.getMessage() : authException.getMessage();

        response.getWriter().write("{ \"error\": \"Unauthorized\", \"message\": \"" + message + "\" }");
    }
}