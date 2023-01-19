package com.playlist.cassette.config.security.jwt;


import com.playlist.cassette.handler.exception.ExceptionCode;
import com.playlist.cassette.handler.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final String contentType = "application/json;charset=UTF-8";
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, UserException {
        response.setStatus(HttpServletResponse.SC_OK);
        ExceptionCode exceptionCode = (ExceptionCode)request.getAttribute("exception");
        if(exceptionCode != null) {
            if (exceptionCode.equals(ExceptionCode.EMPTY_TOKEN)) {
                setResponse(response, ExceptionCode.EMPTY_TOKEN);
            } else if (exceptionCode.equals(ExceptionCode.EXPIRED_JWT_TOKEN)) {
                setResponse(response, ExceptionCode.EXPIRED_JWT_TOKEN);
            } else if (exceptionCode.equals(ExceptionCode.INVALID_JWT_SIGNATURE)) {
                setResponse(response, ExceptionCode.INVALID_JWT_SIGNATURE);
            } else if (exceptionCode.equals(ExceptionCode.INVALID_JWT_TOKEN)) {
                setResponse(response, ExceptionCode.INVALID_JWT_TOKEN);
            } else if (exceptionCode.equals(ExceptionCode.UNSUPPORTED_JWT_TOKEN)) {
                setResponse(response, ExceptionCode.UNSUPPORTED_JWT_TOKEN);
            }
        }
    }

    private void setResponse(HttpServletResponse response, ExceptionCode exceptionCode) throws IOException {
        response.setContentType(contentType);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("timestamp", String.valueOf(LocalDateTime.now()));
        jsonObject.put("code", exceptionCode.getCode());
        jsonObject.put("message", exceptionCode.getMessage());
        response.getWriter().print(jsonObject);
    }
}
