package com.playlist.cassette.service;

import com.playlist.cassette.dto.tape.TapeListResponseDto;
import com.playlist.cassette.dto.tape.TapeResponseDto;
import com.playlist.cassette.dto.tape.TapeSaveRequestDto;
import com.playlist.cassette.dto.tape.TapeUpdateRequestDto;
import com.playlist.cassette.dto.track.TrackResponseDto;
import com.playlist.cassette.entity.Tape;
import com.playlist.cassette.repository.TapeRepository;
import com.playlist.cassette.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TapeService {

    private final TapeRepository tapeRepository;
    private final TrackRepository trackRepository;

    public TapeResponseDto getTape(Long id) {
        Tape tape = tapeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Tape이 없습니다."));

        return TapeResponseDto.builder().tape(tape).build();
    }

    public TapeResponseDto createTape(TapeSaveRequestDto requestDto) {
        Tape tape = tapeRepository.save(requestDto.toEntity());

        return TapeResponseDto.builder().tape(tape).build();
    }

    public TapeResponseDto updateTape(Long id, TapeUpdateRequestDto requestDto) {
        Tape tape = tapeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Tape이 없습니다."));

        tape.update(requestDto.getColorCode(), requestDto.getName());
        tapeRepository.save(tape);

        return TapeResponseDto.builder().tape(tape).build();
    }

    public ResponseEntity<byte[]> downloadTape(Long id, String dirName) throws IOException {
        //List<TrackResponseDto> track = (List<TrackResponseDto>) trackRepository.findByTapeId(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
