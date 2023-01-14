package com.playlist.cassette.dto.tape;

import com.playlist.cassette.entity.Member;
import com.playlist.cassette.entity.Tape;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TapeSaveRequestDto {
    private String colorCode;
    private String name;

    @Builder
    public TapeSaveRequestDto(String colorCode, String name) {
        this.colorCode = colorCode;
        this.name = name;
    }

    public Tape toEntity(Member member) {
        return Tape.builder()
                .member(member)
                .colorCode(colorCode)
                .name(name)
                .build();
    }
}
