package com.playlist.cassette.handler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {
    INVALID_INPUT_NAME_IN_GUEST_FORM(HttpStatus.BAD_REQUEST, "INVALID_INPUT_NAME_IN_GUEST_FORM", "입력한 이름이 올바르지 않습니다."),
    INVALID_MEMBER(HttpStatus.NOT_FOUND, "INVALID_MEMBER_OR_NOT_FOUND", "올바르지 않는 멤버 또는 존재하지 않습니다." ),
    ALREADY_EXIST_MEMBER(HttpStatus.BAD_REQUEST, "ALREADY_EXIST_MEMBER", "이미 존재하는 멤버입니다."),
    ALREADY_WITHDRAWAL_MEMBER(HttpStatus.NOT_FOUND, "ALREADY_WITHDRAWAL_MEMBER", "탈퇴한 멩버입니다."),
    INVALID_SOCIAL_LOGIN_MEMBER(HttpStatus.BAD_REQUEST, "INVALID_SOCIAL_LOGIN_MEMBER", "멤버의 소셜로그인 타입이 존재하지 않습니다."),

    // Auth Exception
    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "EXPIRED_JWT_TOKEN", "만료된 토큰입니다."),
    INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, "INVALID_JWT_SIGNATURE", "토큰 생성키가 올바르지 않습니다."),
    INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "INVALID_JWT_TOKEN", "올바르지 않는 토큰입니다."),
    UNSUPPORTED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "UNSUPPORTED_JWT_TOKEN", "지원하지 않는 토큰입니다."),
    EMPTY_TOKEN(HttpStatus.UNAUTHORIZED, "EMPTY_TOKEN", "유저 인증이 올바르지 않습니다.");

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
