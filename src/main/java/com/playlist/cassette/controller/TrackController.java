package com.playlist.cassette.controller;

import com.playlist.cassette.dto.track.TrackResponseDto;
import com.playlist.cassette.dto.track.TrackSaveRequestDto;
import com.playlist.cassette.entity.Track;
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
public class TrackController {

    private final TrackService trackService;

    @PostMapping("/track/create")
    public ResponseEntity<Object> createTrack(TrackSaveRequestDto requestDto,
                                              @RequestParam("data") MultipartFile multipartFile) throws IOException {
        TrackResponseDto track = trackService.createTrack(requestDto.toEntity(), multipartFile, "audio");

        return ResponseHandler.generateResponse(HttpStatus.OK, track);
    }

}