package com.playlist.cassette.client;

import com.playlist.cassette.dto.auth.SocialLoginDto;
import com.playlist.cassette.entity.Member;

public interface SocialLoginUser {

    Member signup(SocialLoginDto socialLoginDto);
    void withdrawal();
}
