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

    @Builder
    public TapeGuestResponseDto(Tape tape) {
        this.colorCode = tape.getColorCode();
        this.title = tape.getTitle();
        this.name = tape.getName();
    }
}
