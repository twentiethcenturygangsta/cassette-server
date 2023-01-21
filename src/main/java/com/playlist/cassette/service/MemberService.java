package com.playlist.cassette.service;

import com.playlist.cassette.dto.auth.KakaoInfo;
import com.playlist.cassette.dto.member.MemberResponseDto;
import com.playlist.cassette.entity.Member;
import com.playlist.cassette.entity.Tape;
import com.playlist.cassette.handler.exception.ExceptionCode;
import com.playlist.cassette.handler.exception.UserException;
import com.playlist.cassette.repository.MemberRepository;
import com.playlist.cassette.repository.TapeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final TapeRepository tapeRepository;

    public MemberResponseDto getMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new UserException(ExceptionCode.INVALID_MEMBER, ExceptionCode.INVALID_MEMBER.getMessage()));
        if (isWithdrawalMember(member)) {
            throw new UserException(ExceptionCode.ALREADY_WITHDRAWAL_MEMBER, ExceptionCode.ALREADY_WITHDRAWAL_MEMBER.getMessage());
        }
        return MemberResponseDto.builder().member(member).build();
    }

    public Member createMember(Member member) {
        if (!isExistMember(member)) {
            memberRepository.save(member);
            return member;
        }
        Member existedMember = memberRepository.findByKakaoMemberId(member.getKakaoMemberId()).orElseThrow(() ->
                        new UserException(ExceptionCode.INVALID_MEMBER, ExceptionCode.INVALID_MEMBER.getMessage()));
        updateRejoinMember(existedMember);
        return existedMember;
    }

    public MemberResponseDto removeMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new UserException(ExceptionCode.INVALID_MEMBER, ExceptionCode.INVALID_MEMBER.getMessage()));
        removeMemberTapes(member.getTapes());
        memberRepository.delete(member);
        return MemberResponseDto.builder().member(member).build();
    }

    private boolean isExistMember(Member member) {
        return memberRepository.existsByKakaoMemberId(member.getKakaoMemberId());
    }

    private boolean isWithdrawalMember(Member member) {
        return member.getIsRemoved();
    }

    private void updateRejoinMember(Member member) {
        if (member.getIsRemoved()) {
            member.updateUnRemovedStatus();
            memberRepository.save(member);
        }
    }

    private void removeMemberTapes(List<Tape> tapes) {
        for (Tape tape : tapes) {
            tape.updateRemovedStatus();
            tapeRepository.save(tape);
        }
    }
}
