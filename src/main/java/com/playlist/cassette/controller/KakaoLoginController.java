package com.playlist.cassette.controller;

import com.playlist.cassette.dto.auth.KakaoAccount;
import com.playlist.cassette.dto.auth.KakaoInfo;
import com.playlist.cassette.dto.member.MemberResponseDto;
import com.playlist.cassette.entity.Member;
import com.playlist.cassette.handler.response.ResponseHandler;
import com.playlist.cassette.service.KakaoLoginService;
import com.playlist.cassette.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;


@Slf4j
@RestController
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;
    private final MemberService memberService;

    @GetMapping("/callback")
    public ResponseEntity<Object> getSocialLogin(@RequestParam("code") String code) throws URISyntaxException {
        Member kakaoMember = kakaoLoginService.createMember(code);
        MemberResponseDto member = memberService.createMember(kakaoMember);
        return ResponseHandler.generateResponse(HttpStatus.OK, member);
    }
}
