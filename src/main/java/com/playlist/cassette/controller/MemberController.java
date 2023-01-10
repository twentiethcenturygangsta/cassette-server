package com.playlist.cassette.controller;

import com.playlist.cassette.dto.member.MemberResponseDto;
import com.playlist.cassette.handler.response.ResponseHandler;
import com.playlist.cassette.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getMember(@PathVariable String id) {
        Long memberId = Long.valueOf(id);
        MemberResponseDto member = memberService.getMember(memberId);
        return ResponseHandler.generateResponse(HttpStatus.OK, member);
    }
}
