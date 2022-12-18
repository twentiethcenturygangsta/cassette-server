package com.playlist.cassette.handler.exception;

import com.playlist.cassette.handler.response.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<Error> badRequestExceptionHandle(UserException e) {
        Error errorResult = new Error(e.getExceptionCode().getCode(), e.getExceptionCode().getMessage());
        return new ResponseEntity<>(errorResult, e.getExceptionCode().getStatusCode());
    }
}
