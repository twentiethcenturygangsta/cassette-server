package com.playlist.cassette.controller;

import com.playlist.cassette.dto.tape.TapeListResponseDto;
import com.playlist.cassette.dto.tape.TapeResponseDto;
import com.playlist.cassette.dto.tape.TapeSaveRequestDto;
import com.playlist.cassette.dto.tape.TapeUpdateRequestDto;
import com.playlist.cassette.handler.response.ResponseHandler;
import com.playlist.cassette.service.TapeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tape")
public class TapeController {

    private final TapeService tapeService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTape(@PathVariable("id") String id) {
        Long tapeId = Long.valueOf(id);
        List<TapeListResponseDto> tape = tapeService.getTape(tapeId);
        return ResponseHandler.generateResponse(HttpStatus.OK, tape);
    }

    @PostMapping
    public ResponseEntity<Object> createTape(@RequestBody TapeSaveRequestDto requestDto, Principal principal) {
        Long memberId = Long.valueOf(principal.getName());
        TapeResponseDto tape = tapeService.createTape(memberId, requestDto);

        return ResponseHandler.generateResponse(HttpStatus.OK, tape);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTape(@PathVariable("id") String id,
                                             @RequestBody TapeUpdateRequestDto requestDto) {
        Long tapeId = Long.valueOf(id);
        TapeResponseDto tape = tapeService.updateTape(tapeId, requestDto);
        return ResponseHandler.generateResponse(HttpStatus.OK, tape);
    }

    @GetMapping(path = "/download/{id}")
    public ResponseEntity<byte[]> downloadTape(@PathVariable("id") String id) throws IOException {
        Long tapeId = Long.valueOf(id);
        return tapeService.downloadTape(tapeId, "audio");
    }

}
