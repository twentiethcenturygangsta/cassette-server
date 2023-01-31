package com.playlist.cassette.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class MemberWithdrawalLog {

    @Id
    @GeneratedValue
    @Column(name = "member_withdrawal_log_id")
    private Long id;

    private MemberWithdrawalType withdrawalType;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
