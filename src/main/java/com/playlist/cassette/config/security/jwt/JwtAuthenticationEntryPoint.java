package com.playlist.cassette.config.security.jwt;


import com.playlist.cassette.handler.exception.ExceptionCode;
import com.playlist.cassette.handler.exception.UserException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final String AUTHORIZATION_EXCEPTION_ERROR_MESSAGE = "unauthorized access token";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException, UserException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, AUTHORIZATION_EXCEPTION_ERROR_MESSAGE);
    }

}
