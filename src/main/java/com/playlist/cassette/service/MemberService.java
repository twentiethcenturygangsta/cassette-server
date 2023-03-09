package com.playlist.cassette.service;

import com.playlist.cassette.dto.member.MemberResponseDto;
import com.playlist.cassette.dto.member.MemberWithdrawalRequestDto;
import com.playlist.cassette.dto.member.MemberWithdrawalResponseDto;
import com.playlist.cassette.entity.Member;
import com.playlist.cassette.entity.MemberWithdrawalLog;
import com.playlist.cassette.entity.Tape;
import com.playlist.cassette.handler.exception.ExceptionCode;
import com.playlist.cassette.handler.exception.UserException;
import com.playlist.cassette.repository.MemberRepository;
import com.playlist.cassette.repository.MemberWithdrawalLogRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberWithdrawalLogRepository memberWithdrawalLogRepository;

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
    @Transactional
    public MemberWithdrawalResponseDto removeMember(
            Long memberId,
            MemberWithdrawalRequestDto memberWithdrawalRequestDto,
            Cookie refreshToken,
            HttpServletResponse response
    ) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new UserException(ExceptionCode.INVALID_MEMBER, ExceptionCode.INVALID_MEMBER.getMessage()));
        removeMemberTapes(member.getTapes());
        memberRepository.delete(member);

        MemberWithdrawalLog memberWithdrawalLog = MemberWithdrawalLog.builder()
                .withdrawalType(memberWithdrawalRequestDto.getWithdrawalType())
                .withdrawalReason(memberWithdrawalRequestDto.getWithdrawalReason())
                .build();
        memberWithdrawalLogRepository.save(memberWithdrawalLog);

        removeRefreshTokenInCookie(refreshToken, response);

        return MemberWithdrawalResponseDto.builder()
                .member(member)
                .memberWithdrawalLog(memberWithdrawalLog)
                .build();
    }

    public String logout(Cookie refreshToken, HttpServletResponse response) {
        return removeRefreshTokenInCookie(refreshToken, response);
    }

    private String removeRefreshTokenInCookie(Cookie refreshToken, HttpServletResponse response) {
        refreshToken.setMaxAge(0);
        refreshToken.setPath("/");
        response.addCookie(refreshToken);
        return refreshToken.getValue();
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
            tape.setMemberToNull();
        }
    }

}
