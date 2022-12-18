package com.playlist.cassette.handler.exception;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException{
    private String exceptionCode;

    public UserException() {
        super();
    }

    public UserException(String exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public UserException(String message, Throwable cause) {
        super(message,cause);
    }

    public UserException(Throwable cause) {
        super(cause);
    }

    protected UserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
