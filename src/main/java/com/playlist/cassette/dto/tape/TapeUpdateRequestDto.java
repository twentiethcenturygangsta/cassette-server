package com.playlist.cassette.dto.tape;

import com.playlist.cassette.entity.Tape;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TapeUpdateRequestDto {
    private String colorCode;
    private String title;

    @Builder
    public TapeUpdateRequestDto(String colorCode, String title) {
        this.colorCode = colorCode;
        this.title = title;
    }

    public Tape toEntity() {
        return Tape.builder()
                .colorCode(colorCode)
                .title(title)
                .build();
    }
}
