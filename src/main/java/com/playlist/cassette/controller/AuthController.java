package com.playlist.cassette.controller;

import com.playlist.cassette.dto.auth.JwtTokenDto;
import com.playlist.cassette.handler.response.ResponseHandler;
import com.playlist.cassette.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshAuthToken(@RequestHeader("authorization") String refreshToken) {
        JwtTokenDto jwtTokenDto = authService.refreshToken(refreshToken);
        return ResponseHandler.generateResponse(HttpStatus.OK, jwtTokenDto);
    }

}
