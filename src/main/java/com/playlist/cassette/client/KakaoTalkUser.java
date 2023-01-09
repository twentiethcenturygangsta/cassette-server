package com.playlist.cassette.client;

import com.playlist.cassette.dto.auth.SocialLoginDto;
import com.playlist.cassette.entity.Member;

public class KakaoTalkUser implements SocialLoginUser{

    @Override
    public Member signup(SocialLoginDto socialLoginDto) {
        return Member.builder()
                .name(socialLoginDto.getNickName())
                .age(socialLoginDto.getAgeRange())
                .gender(socialLoginDto.getGender())
                .email(socialLoginDto.getEmail())
                .kakaoMemberId(socialLoginDto.getId())
                .build();
    }

    @Override
    public void withdrawal() {}
}
