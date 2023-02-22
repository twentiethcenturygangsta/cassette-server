package com.playlist.cassette.dto.tape;

import com.playlist.cassette.entity.Tape;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TapeGuestResponseDto {
    private String colorCode;
    private String title;
    private String name;
    private Boolean hasAudioLink;

    @Builder
    public TapeGuestResponseDto(Tape tape) {
        this.colorCode = tape.getColorCode();
        this.title = tape.getTitle();
        this.name = tape.getName();
        this.hasAudioLink = hasAudioLink(tape);
    }

    private Boolean hasAudioLink(Tape tape) {
        return tape.getTracks().stream().filter((instance) -> !instance.getIsRemoved()).count() == 12;
    }
}
