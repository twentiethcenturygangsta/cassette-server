package com.playlist.cassette.repository.auth;

import com.playlist.cassette.config.KakaoFeignConfiguration;
import com.playlist.cassette.dto.auth.KakaoProfile;
import com.playlist.cassette.dto.auth.KakaoToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@FeignClient(name = "kakaoClient", configuration = KakaoFeignConfiguration.class)
public interface KakaoLoginRepository {

    @PostMapping
    KakaoProfile getProfile(URI baseUrl, @RequestHeader("Authorization") String accessToken);

    @PostMapping
    KakaoToken getToken(URI baseUrl, @RequestParam("client_id") String restAPIKey,
                        @RequestParam("redirect_uri") String redirectUrl,
                        @RequestParam("code") String code,
                        @RequestParam("grant_type") String grantType);

}
