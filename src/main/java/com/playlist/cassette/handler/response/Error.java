package com.playlist.cassette.handler.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Error {
    private String timestamp;
    private String code;
    private String message;
}
