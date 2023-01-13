package com.playlist.cassette.controller;

import com.playlist.cassette.dto.tape.TapeResponseDto;
import com.playlist.cassette.dto.tape.TapeSaveRequestDto;
import com.playlist.cassette.dto.tape.TapeUpdateRequestDto;
import com.playlist.cassette.entity.Tape;
import com.playlist.cassette.handler.response.ResponseHandler;
import com.playlist.cassette.service.TapeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TapeController {

    private final TapeService tapeService;

    @PostMapping("/tape")
    public ResponseEntity<Object> createTape(TapeSaveRequestDto requestDto) {
        TapeResponseDto tape = tapeService.createTape(requestDto);

        return ResponseHandler.generateResponse(HttpStatus.OK, tape);
    }

    @PutMapping("/tape/{id}")
    public ResponseEntity<Object> updateTape(@PathVariable("id") String id,
                                             TapeUpdateRequestDto requestDto) {
        Long tapeId = Long.valueOf(id);
        TapeResponseDto tape = tapeService.updateTape(tapeId, requestDto);
        return ResponseHandler.generateResponse(HttpStatus.OK, tape);
    }

}
