package com.playlist.cassette.dto.member;

import com.playlist.cassette.entity.Member;
import com.playlist.cassette.entity.SocialLoginType;
import com.playlist.cassette.handler.exception.ExceptionCode;
import com.playlist.cassette.handler.exception.UserException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponseDto {
    private String name;
    private String email;
    private SocialLoginType socialLoginType;

    @Builder
    public MemberResponseDto(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.socialLoginType = getSocialLoginType(member);
    }

    private SocialLoginType getSocialLoginType(Member member) {
        if (member.getKakaoMemberId() != null) {
            return SocialLoginType.KAKAO;
        }
        throw new UserException(
                ExceptionCode.INVALID_SOCIAL_LOGIN_MEMBER,
                ExceptionCode.INVALID_SOCIAL_LOGIN_MEMBER.getMessage());
    }
}
