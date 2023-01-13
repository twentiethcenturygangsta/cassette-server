package com.playlist.cassette.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Track extends BaseAuditEntity{

    @Id
    @GeneratedValue
    @Column(name = "track_id")
    private Long id;
    private Long tapeId;
    private String name;
    private String senderName;
    private String fileName;
    private String audioLink;

    @Builder
    public Track(Long tapeId, String name, String senderName, String fileName, String audioLink) {
        this.tapeId = tapeId;
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
