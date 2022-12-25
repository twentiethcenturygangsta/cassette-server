package com.playlist.cassette.controller;

import com.playlist.cassette.dto.auth.KakaoAccount;
import com.playlist.cassette.dto.auth.KakaoInfo;
import com.playlist.cassette.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;


@Slf4j
@RestController
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/callback")
    public KakaoInfo getKakaoProfile(@RequestParam("code") String code) throws URISyntaxException {
        return kakaoLoginService.getProfile(code);
    }
}
