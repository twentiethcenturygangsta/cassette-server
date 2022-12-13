package com.playlist.cassette.handler.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", status.value());
        map.put("result", data);

        return new ResponseEntity<Object>(map,status);
    }
}
