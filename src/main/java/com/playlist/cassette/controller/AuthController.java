package com.playlist.cassette.controller;

import com.playlist.cassette.dto.auth.JwtTokenDto;
import com.playlist.cassette.handler.response.ResponseHandler;
import com.playlist.cassette.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshAuthToken(
            @RequestHeader("authorization") String refreshToken,
            HttpServletResponse response
    ) {
        JwtTokenDto jwtTokenDto = authService.refreshToken(refreshToken, response);
        return ResponseHandler.generateResponse(HttpStatus.OK, jwtTokenDto);
    }

}
