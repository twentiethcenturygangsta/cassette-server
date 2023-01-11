package com.playlist.cassette.service;

import com.playlist.cassette.dto.auth.KakaoInfo;
import com.playlist.cassette.dto.member.MemberResponseDto;
import com.playlist.cassette.entity.Member;
import com.playlist.cassette.handler.exception.ExceptionCode;
import com.playlist.cassette.handler.exception.UserException;
import com.playlist.cassette.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponseDto getMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new UserException(ExceptionCode.INVALID_MEMBER, ExceptionCode.INVALID_MEMBER.getMessage()));
        if (isWithdrawalMember(member)) {
            throw new UserException(ExceptionCode.ALREADY_WITHDRAWAL_MEMBER, ExceptionCode.ALREADY_WITHDRAWAL_MEMBER.getMessage());
        }
        return MemberResponseDto.builder().member(member).build();
    }

    public MemberResponseDto createMember(Member member) {
        if (!isExistMember(member)) {
            memberRepository.save(member);
            return MemberResponseDto.builder().member(member).build();
        }
        Member existedMember = memberRepository.findByKakaoMemberId(member.getKakaoMemberId()).orElseThrow(() ->
                        new UserException(ExceptionCode.INVALID_MEMBER, ExceptionCode.INVALID_MEMBER.getMessage()));
        updateRejoinMember(existedMember);
        return MemberResponseDto.builder().member(existedMember).build();
    }

    public MemberResponseDto removeMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new UserException(ExceptionCode.INVALID_MEMBER, ExceptionCode.INVALID_MEMBER.getMessage()));
        member.updateRemovedStatus();
        memberRepository.save(member);
        return MemberResponseDto.builder().member(member).build();
    }

    private boolean isExistMember(Member member) {
        return memberRepository.existsByKakaoMemberId(member.getKakaoMemberId());
    }

    private boolean isWithdrawalMember(Member member) {
        return member.getIsRemoved();
    }

    private Member updateRejoinMember(Member member) {
        if (member.getIsRemoved()) {
            member.updateUnRemovedStatus();
            memberRepository.save(member);
        }
        return member;
    }
}
