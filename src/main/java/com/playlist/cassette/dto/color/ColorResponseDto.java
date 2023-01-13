package com.playlist.cassette.dto.color;

import com.playlist.cassette.entity.Color;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ColorResponseDto {
    private String code;

    @Builder
    public ColorResponseDto(Color color) {
        this.code = color.getCode();
    }
}
