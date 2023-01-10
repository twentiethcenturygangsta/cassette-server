package com.playlist.cassette.service;

import com.playlist.cassette.client.KakaoTalkUser;
import com.playlist.cassette.dto.auth.KakaoInfo;
import com.playlist.cassette.dto.auth.KakaoToken;
import com.playlist.cassette.client.KakaoClient;
import com.playlist.cassette.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.net.URISyntaxException;


@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoLoginService {

    private final KakaoClient kakaoClient;
    private final String AUTHORIZATION_CODE = "authorization_code";
    @Value("${kakao.authUrl}")
    private String kakaoAuthUrl;

    @Value("${kakao.userApiUrl}")
    private String kakaoUserApiUrl;

    @Value("${kakao.restApiKey}")
    private String restAPIKey;

    @Value("${kakao.redirectUrl}")
    private String redirectUrl;

    private KakaoInfo getProfile(String code) throws URISyntaxException {
        KakaoToken kakaoToken = getToken(code);

        return kakaoClient.getProfile(
                new URI(kakaoUserApiUrl),
                kakaoToken.getTokenType() + " " + kakaoToken.getAccessToken()
        );
    }

    private KakaoToken getToken(String code) throws URISyntaxException {
        return kakaoClient.getToken(new URI(kakaoAuthUrl), restAPIKey, redirectUrl, code, AUTHORIZATION_CODE);
    }

    public Member createMember(String code) throws URISyntaxException {
        KakaoInfo kakaoInfo = getProfile(code);
        KakaoTalkUser kakaoTalkUser = new KakaoTalkUser();
        return kakaoTalkUser.signup(kakaoInfo);
    }
}
