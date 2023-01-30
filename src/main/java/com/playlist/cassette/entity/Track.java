package com.playlist.cassette.entity;

import com.twentiethcenturygangsta.jamboard.trace.JamBoardEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JamBoardEntity(group="cassette111", description = "Track 정보")

@Entity
public class Track extends BaseAuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "track_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tape_id")
    private Tape tape;
    private String colorCode;
    private String title;
    private String name;
    private String fileName;
    private String audioLink;

    @Builder
    public Track(Tape tape, String colorCode, String title, String name, String fileName, String audioLink) {
        this.tape = tape;
        this.colorCode = colorCode;
        this.title = title;
        this.name = name;
        this.fileName = fileName;
        this.audioLink = audioLink;
    }

    public void update(String fileName, String audioLink) {
        this.fileName = fileName;
        this.audioLink = audioLink;
    }
}
