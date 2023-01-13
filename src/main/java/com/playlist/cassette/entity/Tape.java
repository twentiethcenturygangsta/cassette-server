package com.playlist.cassette.entity;

import com.playlist.cassette.random.RandomString;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Tape extends BaseAuditEntity {
    @Id
    @GeneratedValue
    @Column(name = "tape_id")
    private Long id;
    private Long memberId;
    private String colorCode;
    private String name;
    private String tapeLink;
    private String audioLink;

    @Builder
    public Tape(Long memberId, String colorCode, String name, String audioLink) {
        this.memberId = memberId;
        this.colorCode = colorCode;
        this.name = name;
        this.tapeLink = RandomString.RandomString();
        this.audioLink = audioLink;
    }

    public void update(String colorCode, String name) {
        this.colorCode = colorCode;
        this.name = name;
    }

    public void updateAudioLink(String audioLink) {
        this.audioLink = audioLink;
    }
}
