package com.playlist.cassette.service;

import com.playlist.cassette.config.security.jwt.JwtTokenProvider;
import com.playlist.cassette.config.security.jwt.JwtValidationType;
import com.playlist.cassette.config.security.jwt.UserAuthentication;
import com.playlist.cassette.dto.auth.JwtTokenDto;
import com.playlist.cassette.dto.member.LoginResponseDto;
import com.playlist.cassette.dto.member.MemberResponseDto;
import com.playlist.cassette.entity.Member;
import com.playlist.cassette.handler.exception.ExceptionCode;
import com.playlist.cassette.handler.exception.UserException;
import com.playlist.cassette.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final long REFRESH_TOKEN_EXPIRATION_TIME_GAP = 604800000;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponseDto createLoginMember(Member member) {
        Authentication authentication = new UserAuthentication(member.getId(), null, null);
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        member.updateRefreshToken(refreshToken);
        memberRepository.save(member);

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

    public JwtTokenDto refreshToken(String token) {
        String refreshToken = token.substring(7);
        JwtValidationType jwtValidationType = jwtTokenProvider.validateToken(refreshToken);
        String accessToken = "";
        if (jwtValidationType.equals(JwtValidationType.VALID_JWT)) {
            Member member = isValidMemberWithRefreshTokenInDatabase(refreshToken);
            Authentication authentication = new UserAuthentication(member.getId(), null, null);
            accessToken = jwtTokenProvider.generateAccessToken(authentication);
            if (isExpiredRefreshToken(member)) {
                refreshToken = jwtTokenProvider.generateRefreshToken(authentication);
            }
        }
        return JwtTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private Member isValidMemberWithRefreshTokenInDatabase(String refreshToken) {
        Member member = memberRepository.findByRefreshToken(refreshToken).orElseThrow(() ->
                new UserException(ExceptionCode.INVALID_MEMBER, ExceptionCode.INVALID_MEMBER.getMessage()));
        if (!member.getRefreshToken().equals(refreshToken)) {
            throw new UserException(ExceptionCode.INVALID_JWT_TOKEN, ExceptionCode.INVALID_JWT_TOKEN.getMessage());
        }
        return member;
    }

    private boolean isExpiredRefreshToken(Member member) {
        Date today = new Date();
        return (today.getTime() - member.getRefreshTokenExpireTime().getTime()) / 1000 > REFRESH_TOKEN_EXPIRATION_TIME_GAP;
    }
}

