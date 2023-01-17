package com.playlist.cassette.controller;

import com.playlist.cassette.dto.tape.*;
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

    @GetMapping
    public ResponseEntity<Object> getTapes(Principal principal) {
        Long memberId = Long.valueOf(principal.getName());
        List<TapeListResponseDto> tape = tapeService.getTapes(memberId);
        return ResponseHandler.generateResponse(HttpStatus.OK, tape);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Object> getTape(@PathVariable("uuid") String tapeLink) {
        TapeGuestResponseDto tape = tapeService.getTape(tapeLink);
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
