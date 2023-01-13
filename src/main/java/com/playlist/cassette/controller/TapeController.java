package com.playlist.cassette.controller;

import com.playlist.cassette.dto.tape.TapeListResponseDto;
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

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TapeController {

    private final TapeService tapeService;

    @GetMapping("/tape/{id}")
    public ResponseEntity<Object> getTape(@PathVariable("id") String id) {
        Long tapeId = Long.valueOf(id);
        TapeResponseDto tape = tapeService.getTape(tapeId);
        //List<TapeListResponseDto> tape = tapeService.getTape(tapeId);
        return ResponseHandler.generateResponse(HttpStatus.OK, tape);
    }

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

    @GetMapping(path = "/tape/download/{id}")
    public ResponseEntity<byte[]> downloadTape(@PathVariable("id") String id) throws IOException {
        Long tapeId = Long.valueOf(id);
        return tapeService.downloadTape(tapeId, "audio");
    }

}
