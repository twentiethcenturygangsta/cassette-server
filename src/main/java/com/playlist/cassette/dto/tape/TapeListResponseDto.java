package com.playlist.cassette.dto.tape;

import com.playlist.cassette.dto.track.TrackResponseDto;
import com.playlist.cassette.entity.Member;
import com.playlist.cassette.entity.Tape;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TapeListResponseDto {
    private Long id;
    private String colorCode;
    private String title;
    private String name;
    private String tapeLink;
    private String fileName;
    private String audioLink;
    private String createAt;
    private List<TrackResponseDto> tracks;

    public TapeListResponseDto(Tape tape) {
        this.id = tape.getId();
        this.colorCode = tape.getColorCode();
        this.title = tape.getTitle();
        this.name = tape.getName();
        this.tapeLink = tape.getTapeLink();
        this.fileName = tape.getFileName();
        this.audioLink = tape.getAudioLink();
        this.createAt = String.valueOf(tape.getCreatedAt());
        this.tracks = tape.getTracks().stream().map(TrackResponseDto::new).collect(Collectors.toList());
    }
}
