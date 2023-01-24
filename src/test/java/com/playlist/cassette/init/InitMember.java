package com.playlist.cassette.init;

import com.playlist.cassette.entity.Member;

import java.util.Date;

public class InitMember {
    public Member createDefaultMember() {
        return Member.builder()
                .name("김중앙")
                .email("test1@cassette.com")
                .gender("male")
                .age("20-29")
                .kakaoMemberId(1L)
                .build();
    }
}
