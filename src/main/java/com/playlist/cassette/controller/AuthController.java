package com.playlist.cassette.controller;

import com.playlist.cassette.dto.auth.JwtTokenDto;
import com.playlist.cassette.handler.response.ResponseHandler;
import com.playlist.cassette.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshAuthToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Cookie[] list = request.getCookies();
        String refreshToken = "";
        for(Cookie cookie:list) {
            if(cookie.getName().equals("refreshToken")) {
                refreshToken = cookie.getValue();
            }
        }
        JwtTokenDto jwtTokenDto = authService.refreshToken(refreshToken, response);
        return ResponseHandler.generateResponse(HttpStatus.OK, jwtTokenDto);
    }

}
