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
    private String name;

    @Builder
    public TapeUpdateRequestDto(String colorCode, String name) {
        this.colorCode = colorCode;
        this.name = name;
    }

    public Tape toEntity() {
        return Tape.builder()
                .colorCode(colorCode)
                .name(name)
                .build();
    }
}
