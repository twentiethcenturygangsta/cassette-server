package com.playlist.cassette.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class Tape extends BaseAuditEntity {
    @Id
    @GeneratedValue
    @Column(name = "tape_id")
    private Long id;
    private Long memberId;
    private Long colorId;
    private String name;
    private String audioLink;

    @Builder
    public Tape(Long memberId, Long colorId, String name, String audioLink) {
        this.memberId = memberId;
        this.colorId = colorId;
        this.name = name;
        this.audioLink = audioLink;
    }

    public void update(Long colorId, String audioLink) {
        this.colorId = colorId;
        this.audioLink = audioLink;
    }
}
