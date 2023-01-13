package com.playlist.cassette.dto.tape;

import com.playlist.cassette.dto.track.TrackResponseDto;
import com.playlist.cassette.entity.Tape;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TapeListResponseDto {
    private Long memberId;
    private String colorCode;
    private String name;
    private String tapeLink;
    private String audioLink;
    private List<TrackResponseDto> trackResponseDtos;

    public TapeListResponseDto(Tape tape) {
        this.memberId = tape.getMemberId();
        this.colorCode = tape.getColorCode();
        this.name = tape.getName();
        this.tapeLink = tape.getTapeLink();
        this.audioLink = tape.getAudioLink();
    }
}
