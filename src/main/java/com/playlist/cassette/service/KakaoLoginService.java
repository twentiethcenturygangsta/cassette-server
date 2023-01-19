package com.playlist.cassette.service;

import com.playlist.cassette.client.KakaoTalkUser;
import com.playlist.cassette.dto.auth.KakaoInfo;
import com.playlist.cassette.dto.auth.KakaoToken;
import com.playlist.cassette.client.KakaoClient;
import com.playlist.cassette.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.net.URISyntaxException;


@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoLoginService {

    private final KakaoClient kakaoClient;
    private final Environment env;
    private final String AUTHORIZATION_CODE = "authorization_code";
    @Value("${kakao.authUrl}")
    private String kakaoAuthUrl;

    @Value("${kakao.userApiUrl}")
    private String kakaoUserApiUrl;

    @Value("${kakao.restApiKey}")
    private String restAPIKey;
    private String redirectUrl;

    public Member createMember(String code, String applicationEnv) throws URISyntaxException {
        setRedirectUrl(applicationEnv);
        KakaoInfo kakaoInfo = getProfile(code);
        KakaoTalkUser kakaoTalkUser = new KakaoTalkUser();
        return kakaoTalkUser.signup(kakaoInfo);
    }

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

    private void setRedirectUrl(String applicationEnv) {
        if (applicationEnv.equals("local")) {
            this.redirectUrl = env.getProperty("kakao.redirectUrlLocalServer");
        } else if(applicationEnv.equals("dev")) {
            this.redirectUrl = env.getProperty("kakao.redirectUrlDevServer");
        } else {
            this.redirectUrl = env.getProperty("kakao.redirectUrlProdServer");
        }
    }
}
