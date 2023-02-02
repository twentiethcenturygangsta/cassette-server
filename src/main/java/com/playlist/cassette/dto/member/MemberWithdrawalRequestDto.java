package com.playlist.cassette.dto.member;

import com.playlist.cassette.entity.MemberWithdrawalType;
import lombok.Getter;

@Getter
public class MemberWithdrawalRequestDto {
    private MemberWithdrawalType withdrawalType;
    private String withdrawalReason;
}
