package com.playlist.cassette.controller;

import com.playlist.cassette.dto.member.MemberResponseDto;
import com.playlist.cassette.dto.member.MemberWithdrawalRequestDto;
import com.playlist.cassette.dto.member.MemberWithdrawalResponseDto;
import com.playlist.cassette.handler.response.ResponseHandler;
import com.playlist.cassette.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<Object> getMember(Principal principal) {
        String memberId = principal.getName();
        MemberResponseDto member = memberService.getMember(Long.valueOf(memberId));
        return ResponseHandler.generateResponse(HttpStatus.OK, member);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<Object> removeMember(
            Principal principal,
            @RequestBody MemberWithdrawalRequestDto memberWithdrawalRequestDto,
            @CookieValue("refreshToken") Cookie refreshToken,
            HttpServletResponse response
    ) {
        String memberId = principal.getName();
        MemberWithdrawalResponseDto member = memberService.removeMember(
                Long.valueOf(memberId),
                memberWithdrawalRequestDto,
                refreshToken,
                response
        );
        return ResponseHandler.generateResponse(HttpStatus.OK, member);
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> logout(Principal principal, @CookieValue("refreshToken") Cookie refreshToken, HttpServletResponse response) {
        String cookie = memberService.logout(refreshToken, response);
        return ResponseHandler.generateResponse(HttpStatus.OK, cookie);
    }
}
