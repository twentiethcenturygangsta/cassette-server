package com.playlist.cassette.service;

import com.playlist.cassette.dto.tape.TapeResponseDto;
import com.playlist.cassette.dto.tape.TapeSaveRequestDto;
import com.playlist.cassette.dto.tape.TapeUpdateRequestDto;
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

    public TapeResponseDto updateTape(Long id, TapeUpdateRequestDto requestDto) {
        Tape tape = tapeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Tape이 없습니다."));

        tape.update(requestDto.getColorCode(), requestDto.getName());
        tapeRepository.save(tape);

        return TapeResponseDto.builder().tape(tape).build();
    }

}
