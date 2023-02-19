package com.playlist.cassette.entity;

import com.playlist.cassette.utils.RandomStringUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Tape extends BaseAuditEntity {

    private static final int TAPE_LINK_LENGTH = 6;

    @Id
    @GeneratedValue
    @Column(name = "tape_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "tape", cascade = CascadeType.ALL)
    private final List<Track> tracks = new ArrayList<>();

    private String colorCode;
    private String title;
    private String name;
    private String tapeLink;
    private String fileName;
    private String audioLink;

    @Builder
    public Tape(Member member, String colorCode, String title, String name, String fileName, String audioLink) {
        this.member = member;
        this.colorCode = colorCode;
        this.title = title;
        this.name = name;
        this.tapeLink = RandomStringUtils.getRandomString(TAPE_LINK_LENGTH);
        this.fileName = fileName;
        this.audioLink = audioLink;
    }

    public void update(String colorCode, String title, String name) {
        this.colorCode = colorCode;
        this.title = title;
        this.name = name;
    }

    public void updateAudioLink(String fileName, String audioLink) {
        this.fileName = fileName;
        this.audioLink = audioLink;
    }

    public void setMemberToNull() {
        this.member = null;
    }
}
