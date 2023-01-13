package com.playlist.cassette.entity;

import com.playlist.cassette.utils.RandomStringUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Tape extends BaseAuditEntity {

    private static final int TAPE_LINK_LENGTH = 6;

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
        this.tapeLink = RandomStringUtils.getRandomString(TAPE_LINK_LENGTH);
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
