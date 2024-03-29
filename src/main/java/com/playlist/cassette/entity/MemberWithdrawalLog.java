package com.playlist.cassette.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class MemberWithdrawalLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_withdrawal_log_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private MemberWithdrawalType withdrawalType;

    private String withdrawalReason;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public MemberWithdrawalLog(MemberWithdrawalType withdrawalType, String withdrawalReason) {
        this.withdrawalType = withdrawalType;
        this.withdrawalReason = withdrawalReason;

    }
}
