package com.playlist.cassette.handler.exception;

import com.playlist.cassette.handler.response.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> badRequestExceptionHandle(MethodArgumentNotValidException e) {
        String timestamp = String.valueOf(LocalDateTime.now());

        List<Object> elements = Arrays.asList(e.getBindingResult().getFieldErrors().get(0).getArguments());

        Error errorResult = new Error(
                timestamp,
                ((ExceptionCode) elements.get(1)).getCode(),
                ((ExceptionCode) elements.get(1)).getMessage());
        return new ResponseEntity<>(errorResult, e.getStatusCode());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Error> badRequestFormDataExceptionHandle(BindException e) {
        String timestamp = String.valueOf(LocalDateTime.now());

        List<Object> elements = Arrays.asList();

        Error errorResult = new Error(
                timestamp,
                ((ExceptionCode) elements.get(1)).getCode(),
                ((ExceptionCode) elements.get(1)).getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Error> internalServerExceptionHandle(Exception e) {
        String timestamp = String.valueOf(LocalDateTime.now());
        Error errorResult = new Error(timestamp, "INTERNAL_SERVER_ERROR", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<Error> bindExceptionHandle(BindException e) {
        String timestamp = String.valueOf(LocalDateTime.now());

        List<Object> elements = Arrays.asList(e.getBindingResult().getFieldErrors().get(0).getArguments());

        Error errorResult = new Error(
                timestamp,
                ((ExceptionCode) elements.get(1)).getCode(),
                ((ExceptionCode) elements.get(1)).getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }
}
