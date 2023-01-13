package com.playlist.cassette.service;

import com.playlist.cassette.dto.tape.TapeResponseDto;
import com.playlist.cassette.dto.tape.TapeSaveRequestDto;
import com.playlist.cassette.entity.Tape;
import com.playlist.cassette.repository.TapeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TapeService {

    private final TapeRepository tapeRepository;

    public TapeResponseDto createTape(TapeSaveRequestDto requestDto) {
        Tape tape = tapeRepository.save(requestDto.toEntity());

        return TapeResponseDto.builder().tape(tape).build();
    }

}
