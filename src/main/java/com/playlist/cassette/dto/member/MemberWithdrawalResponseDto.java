package com.playlist.cassette.dto.member;

import com.playlist.cassette.entity.Member;
import com.playlist.cassette.entity.MemberWithdrawalLog;
import com.playlist.cassette.entity.MemberWithdrawalType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberWithdrawalResponseDto {

    private String name;
    private String email;
    private MemberWithdrawalType withdrawalType;

    @Builder
    public MemberWithdrawalResponseDto(Member member, MemberWithdrawalLog memberWithdrawalLog) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.withdrawalType = memberWithdrawalLog.getWithdrawalType();
    }
}
