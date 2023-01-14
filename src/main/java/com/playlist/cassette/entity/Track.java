package com.playlist.cassette.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Track extends BaseAuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "track_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tape_id")
    private Tape tape;
    private String name;
    private String senderName;
    private String fileName;
    private String audioLink;

    @Builder
    public Track(Tape tape, String name, String senderName, String fileName, String audioLink) {
        this.tape = tape;
        this.name = name;
        this.senderName = senderName;
        this.fileName = fileName;
        this.audioLink = audioLink;
    }

    public void update(String fileName, String audioLink) {
        this.fileName = fileName;
        this.audioLink = audioLink;
    }
}
