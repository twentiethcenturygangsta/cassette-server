package com.playlist.cassette.service;

import com.playlist.cassette.config.security.jwt.JwtTokenProvider;
import com.playlist.cassette.config.security.jwt.UserAuthentication;
import com.playlist.cassette.dto.auth.JwtTokenDto;
import com.playlist.cassette.dto.member.LoginResponseDto;
import com.playlist.cassette.dto.member.MemberResponseDto;
import com.playlist.cassette.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponseDto createLoginMember(Member member) {
        Authentication authentication = new UserAuthentication(member.getId(), null, null);
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);
        JwtTokenDto jwtTokenDto = JwtTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        MemberResponseDto memberResponseDto = MemberResponseDto.builder()
                .member(member)
                .build();
        return LoginResponseDto.builder()
                .jwtInformation(jwtTokenDto)
                .memberInformation(memberResponseDto)
                .build();
    }
}
