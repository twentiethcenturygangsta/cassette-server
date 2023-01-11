package com.playlist.cassette.controller;

import com.playlist.cassette.dto.member.MemberResponseDto;
import com.playlist.cassette.handler.response.ResponseHandler;
import com.playlist.cassette.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("{id}")
    public ResponseEntity<Object> getMember(@PathVariable String id) {
        Long memberId = Long.valueOf(id);
        MemberResponseDto member = memberService.getMember(memberId);
        return ResponseHandler.generateResponse(HttpStatus.OK, member);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeMember(@PathVariable String id) {
        Long memberId = Long.valueOf(id);
        MemberResponseDto member = memberService.removeMember(memberId);
        return ResponseHandler.generateResponse(HttpStatus.OK, member);
    }
}
