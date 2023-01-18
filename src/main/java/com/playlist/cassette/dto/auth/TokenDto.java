package com.playlist.cassette.dto.auth;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenDto {
    private String value;
    private Date expiredTime;

    @Builder
    public TokenDto(String value, Date expiredTime) {
        this.value = value;
        this.expiredTime = expiredTime;
    }
}
