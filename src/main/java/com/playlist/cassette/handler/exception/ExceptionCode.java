package com.playlist.cassette.handler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {
    INVALID_INPUT_NAME_IN_GUEST_FORM(HttpStatus.BAD_REQUEST, "INVALID_INPUT_NAME_IN_GUEST_FORM", "입력한 이름이 올바르지 않습니다."),
    INVALID_MEMBER(HttpStatus.NOT_FOUND, "INVALID_MEMBER_OR_NOT_FOUND", "올바르지 않는 멤버 또는 존재하지 않습니다." );

    private final HttpStatus statusCode;
    private final String code;
    private final String message;

    //    Bad Request

    ExceptionCode(HttpStatus statusCode, String code, String message) {
        this.statusCode = statusCode;
        this.code = code;
        this.message = message;

    }
}
