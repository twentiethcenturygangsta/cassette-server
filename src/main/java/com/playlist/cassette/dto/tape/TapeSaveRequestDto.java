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
    private String colorId;
    private String name;

    @Builder
    public TapeSaveRequestDto(String memberId, String colorId, String name) {
        this.memberId = memberId;
        this.colorId = colorId;
        this.name = name;
    }

    public Tape toEntity() {
        return Tape.builder()
                .memberId(Long.valueOf(memberId))
                .colorId(Long.valueOf(colorId))
                .name(name)
                .build();
    }
}
