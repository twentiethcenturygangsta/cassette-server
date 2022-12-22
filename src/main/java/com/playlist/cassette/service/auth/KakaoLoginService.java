package com.playlist.cassette.service.auth;

import com.playlist.cassette.dto.auth.KakaoProfile;
import com.playlist.cassette.dto.auth.KakaoToken;
import com.playlist.cassette.repository.auth.KakaoLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@Service
public class KakaoLoginService {

    private final KakaoLoginRepository kakaoLoginRepository;
    private static final String AUTHORIZATION_CODE = "authorization_code";
    @Value("${kakao.auth-url}")
    private String kakaoAuthUrl;

    @Value("${kakao.user-api-url}")
    private String kakaoUserApiUrl;

    @Value("${kakao.rest-api-key}")
    private String restAPIKey;

    @Value("${kakao.redirect-url}")
    private String redirectUrl;

    public KakaoProfile getProfile(String code) throws URISyntaxException {
        KakaoToken kakaoToken = getToken(code);
        return kakaoLoginRepository.getProfile(
                new URI(kakaoUserApiUrl),
                kakaoToken.getTokenType() + " " + kakaoToken.getAccessToken()
        );
    }

    private KakaoToken getToken(String code) throws URISyntaxException {
        return kakaoLoginRepository.getToken(new URI(kakaoAuthUrl), restAPIKey, redirectUrl, code, AUTHORIZATION_CODE);
    }
}
