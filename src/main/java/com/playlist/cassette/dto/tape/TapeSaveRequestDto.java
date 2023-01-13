package com.playlist.cassette.dto.tape;

import com.playlist.cassette.entity.Tape;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TapeSaveRequestDto {
    private String memberId;
    private String colorCode;
    private String name;

    @Builder
    public TapeSaveRequestDto(String memberId, String colorCode, String name) {
        this.memberId = memberId;
        this.colorCode = colorCode;
        this.name = name;
    }

    public Tape toEntity() {
        return Tape.builder()
                .memberId(Long.valueOf(memberId))
                .colorCode(colorCode)
                .name(name)
                .build();
    }
}
