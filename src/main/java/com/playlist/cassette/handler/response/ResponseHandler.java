package com.playlist.cassette.handler.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object data) {
        LocalDateTime timestamp = LocalDateTime.now();
        Map<String, Object> map = new HashMap<>();
        map.put("message", status.value());
        map.put("timestamp", timestamp);
        map.put("result", data);

        return new ResponseEntity<Object>(map,status);
    }
}
