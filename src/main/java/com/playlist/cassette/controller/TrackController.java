package com.playlist.cassette.controller;

import com.playlist.cassette.dto.track.TrackResponseDto;
import com.playlist.cassette.dto.track.TrackSaveRequestDto;
import com.playlist.cassette.handler.response.ResponseHandler;
import com.playlist.cassette.service.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/track")
public class TrackController {

    private final TrackService trackService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTrack(@PathVariable String id) {
        Long trackId = Long.valueOf(id);
        TrackResponseDto track = trackService.getTrack(trackId);
        return ResponseHandler.generateResponse(HttpStatus.OK, track);
    }

    @PostMapping
    public ResponseEntity<Object> createTrack(TrackSaveRequestDto requestDto,
                                              @RequestParam("data") MultipartFile multipartFile) throws IOException {
        TrackResponseDto track = trackService.createTrack(requestDto, multipartFile, "audio");

        return ResponseHandler.generateResponse(HttpStatus.OK, track);
    }

    @GetMapping(path = "/download/{id}")
    public ResponseEntity<byte[]> downloadTrack(@PathVariable("id") String id) throws IOException {
        Long trackId = Long.valueOf(id);
        return trackService.downloadTrack(trackId, "audio");
    }

}