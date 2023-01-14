package com.playlist.cassette.dto.member;

import com.playlist.cassette.dto.auth.JwtTokenDto;
import com.playlist.cassette.dto.tape.TapeResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDto {
    private MemberResponseDto memberInformation;
    private JwtTokenDto jwtInformation;
    private List<TapeResponseDto> tapes;

    @Builder
    public LoginResponseDto(MemberResponseDto memberInformation, JwtTokenDto jwtInformation, List<TapeResponseDto> tapes) {
        this.memberInformation = memberInformation;
        this.jwtInformation = jwtInformation;
        this.tapes = tapes;
    }
}
