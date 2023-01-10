package com.playlist.cassette.dto.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoInfo implements SocialLoginDto{
    private Long id;
    private KakaoAccount kakaoAccount;

    @Override
    public String getNickName() {
        return this.kakaoAccount.getProfile().getNickname();
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getGender() {
        return this.kakaoAccount.getGender();
    }

    @Override
    public String getAgeRange() {
        return this.kakaoAccount.getAgeRange();
    }

    @Override
    public String getEmail() {
        return this.kakaoAccount.getEmail();
    }
}
