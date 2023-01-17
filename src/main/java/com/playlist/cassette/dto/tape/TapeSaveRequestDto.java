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
    private String title;
    private String name;

    @Builder
    public TapeSaveRequestDto(String colorCode, String title, String name) {
        this.colorCode = colorCode;
        this.title = title;
        this.name = name;
    }

    public Tape toEntity(Member member) {
        return Tape.builder()
                .member(member)
                .colorCode(colorCode)
                .title(title)
                .name(name)
                .build();
    }
}
