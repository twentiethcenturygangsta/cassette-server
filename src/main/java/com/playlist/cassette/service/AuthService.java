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
import org.springframework.http.ResponseCookie;
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

    public JwtTokenDto refreshToken(Cookie token, HttpServletResponse response) {
//        String refreshToken = token.substring(7);
        JwtValidationType jwtValidationType = jwtTokenProvider.validateToken(token.getValue());
        String accessToken = "";
        if (jwtValidationType.equals(JwtValidationType.VALID_JWT)) {
            Member member = isValidMemberWithRefreshTokenInDatabase(token.getValue());
            Authentication authentication = new UserAuthentication(member.getId(), null, null);
            accessToken = jwtTokenProvider.generateAccessToken(authentication).getValue();
            if (isExpiredRefreshToken(member)) {
                TokenDto newRefreshToken = jwtTokenProvider.generateRefreshToken(authentication);
                member.updateRefreshToken(newRefreshToken);
                memberRepository.save(member);
                removeRefreshTokenInCookie(response, token);
                setCookieWithRefreshToken(response, newRefreshToken);
            }
        } else if(jwtValidationType.equals(JwtValidationType.EXPIRED_JWT_TOKEN)) {
            removeRefreshTokenInCookie(response, token);
            throw new UserException(ExceptionCode.EXPIRED_JWT_TOKEN, ExceptionCode.EXPIRED_JWT_TOKEN.getMessage());
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
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken.getValue())
                .path("/")
                .secure(true)
                .httpOnly(true)
                .domain("12playlist.com")
                .maxAge(14*24*60*60)
                .build();

        response.setHeader("Set-Cookie", cookie.toString());
    }

    private void removeRefreshTokenInCookie(HttpServletResponse response, Cookie refreshToken) {
        refreshToken.setMaxAge(0);
        refreshToken.setDomain("12playlist.com");
        refreshToken.setPath("/");
        response.addCookie(refreshToken);
    }
}

