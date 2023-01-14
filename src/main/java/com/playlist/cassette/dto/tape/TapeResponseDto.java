package com.playlist.cassette.dto.tape;

import com.playlist.cassette.entity.Tape;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TapeResponseDto {
    private Long tapeId;
    private String colorCode;
    private String name;
    private String tapeLink;
    private String audioLink;

    @Builder
    public TapeResponseDto(Tape tape) {
        this.tapeId = tape.getId();
        this.colorCode = tape.getColorCode();
        this.name = tape.getName();
        this.tapeLink = tape.getTapeLink();
        this.audioLink = tape.getAudioLink();
    }
}
