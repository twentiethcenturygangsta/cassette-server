package com.playlist.cassette.handler.exception;

import com.playlist.cassette.handler.response.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<Error> badRequestExceptionHandle(UserException e) {
        String timestamp = String.valueOf(LocalDateTime.now());
        Error errorResult = new Error(timestamp, e.getExceptionCode().getCode(), e.getExceptionCode().getMessage());
        return new ResponseEntity<>(errorResult, e.getExceptionCode().getStatusCode());
    }

    @ExceptionHandler
    public ResponseEntity<Error> internalServerExceptionHandle(Exception e) {
        String timestamp = String.valueOf(LocalDateTime.now());
        Error errorResult = new Error(timestamp, "INTERNAL_SERVER_ERROR", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
