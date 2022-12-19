package com.playlist.cassette.service.auth;

import com.playlist.cassette.repository.auth.KakaoLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
@Service
public class KakaoLoginService {

    private KakaoLoginRepository kakaoLoginRepository;

    @Value("${kakao.auth-url}")
    private String kakaoAuthUrl;

    @Value("${kakao.user-api-url}")
    private String kakaoUserApiUrl;

    @Value("${kakao.rest-api-key}")
    private String restAPIKey;

    @Value("${kakao.redirect-url}")
    private String redirectUrl;
}
