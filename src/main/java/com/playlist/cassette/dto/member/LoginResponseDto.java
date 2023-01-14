package com.playlist.cassette.dto.member;

import com.playlist.cassette.dto.auth.JwtTokenDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDto {
    private MemberResponseDto memberInformation;
    private JwtTokenDto jwtInformation;

    @Builder
    public LoginResponseDto(MemberResponseDto memberInformation, JwtTokenDto jwtInformation) {
        this.memberInformation = memberInformation;
        this.jwtInformation = jwtInformation;
    }
}
