package com.playlist.cassette.service;

import com.playlist.cassette.config.security.jwt.JwtTokenProvider;
import com.playlist.cassette.config.security.jwt.JwtValidationType;
import com.playlist.cassette.config.security.jwt.UserAuthentication;
import com.playlist.cassette.dto.auth.JwtTokenDto;
import com.playlist.cassette.dto.auth.TokenDto;
import com.playlist.cassette.dto.member.LoginResponseDto;
import com.playlist.cassette.dto.member.MemberResponseDto;
import com.playlist.cassette.dto.tape.TapeResponseDto;
import com.playlist.cassette.entity.Member;
import com.playlist.cassette.handler.exception.ExceptionCode;
import com.playlist.cassette.handler.exception.UserException;
import com.playlist.cassette.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthService {

    private static final long REFRESH_TOKEN_EXPIRATION_TIME_GAP = 604800000;  // 1week
//    private static final long REFRESH_TOKEN_EXPIRATION_TIME_GAP = 70000;  // 50 seconds

    private static final String REFRESH_TOKEN_SECURE_MESSAGE = "HTTP_ONLY";
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponseDto createLoginMember(Member member, HttpServletResponse response) {
        Authentication authentication = new UserAuthentication(member.getId(), null, null);
        TokenDto accessToken = jwtTokenProvider.generateAccessToken(authentication);
        TokenDto refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        member.updateRefreshToken(refreshToken);
        memberRepository.save(member);

        setCookieWithRefreshToken(response, refreshToken);

        JwtTokenDto jwtTokenDto = JwtTokenDto.builder()
                .accessToken(accessToken.getValue())
                .refreshToken(REFRESH_TOKEN_SECURE_MESSAGE)
                .build();
        MemberResponseDto memberResponseDto = MemberResponseDto.builder()
                .member(member)
                .build();
        List<TapeResponseDto> tapes = member.getTapes().stream().map(TapeResponseDto::new).toList();
        return LoginResponseDto.builder()
                .jwtInformation(jwtTokenDto)
                .memberInformation(memberResponseDto)
                .tapes(tapes)
                .build();
    }

    public JwtTokenDto refreshToken(String token, HttpServletResponse response) {
        String refreshToken = token.substring(7);
        JwtValidationType jwtValidationType = jwtTokenProvider.validateToken(refreshToken);
        String accessToken = "";
        if (jwtValidationType.equals(JwtValidationType.VALID_JWT)) {
            Member member = isValidMemberWithRefreshTokenInDatabase(refreshToken);
            Authentication authentication = new UserAuthentication(member.getId(), null, null);
            accessToken = jwtTokenProvider.generateAccessToken(authentication).getValue();
            if (isExpiredRefreshToken(member)) {
                TokenDto newRefreshToken = jwtTokenProvider.generateRefreshToken(authentication);
                member.updateRefreshToken(newRefreshToken);
                memberRepository.save(member);
                setCookieWithRefreshToken(response, newRefreshToken);
            }
        }
        return JwtTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(REFRESH_TOKEN_SECURE_MESSAGE)
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
        return (member.getRefreshTokenExpireTime().getTime() - today.getTime()) < REFRESH_TOKEN_EXPIRATION_TIME_GAP;
    }

    private void setCookieWithRefreshToken(HttpServletResponse response, TokenDto refreshToken) {

        Cookie cookie = new Cookie("refreshToken", refreshToken.getValue());
        cookie.setMaxAge(14*24*60*60);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
        response.addHeader("REFRESH_TOKEN", refreshToken.getValue());
    }
}

