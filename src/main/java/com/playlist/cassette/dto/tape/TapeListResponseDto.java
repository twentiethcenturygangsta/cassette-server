package com.playlist.cassette.dto.tape;

import com.playlist.cassette.dto.track.TrackResponseDto;
import com.playlist.cassette.entity.Member;
import com.playlist.cassette.entity.Tape;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TapeListResponseDto {
    private String colorCode;
    private String name;
    private String tapeLink;
    private String fileName;
    private String audioLink;
    private List<TrackResponseDto> tracks;

    public TapeListResponseDto(Tape tape) {
        this.colorCode = tape.getColorCode();
        this.name = tape.getName();
        this.tapeLink = tape.getTapeLink();
        this.fileName = tape.getFileName();
        this.audioLink = tape.getAudioLink();
        this.tracks = tape.getTracks().stream().map(TrackResponseDto::new).collect(Collectors.toList());
    }
}
