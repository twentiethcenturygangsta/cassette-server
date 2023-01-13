package com.playlist.cassette.dto.tape;

import com.playlist.cassette.entity.Tape;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TapeResponseDto {
    private Long memberId;
    private Long colorId;
    private String name;
    private String audioLink;

    @Builder
    public TapeResponseDto(Tape tape) {
        this.memberId = tape.getMemberId();
        this.colorId = tape.getColorId();
        this.name = tape.getName();
        this.audioLink = tape.getAudioLink();
    }
}
