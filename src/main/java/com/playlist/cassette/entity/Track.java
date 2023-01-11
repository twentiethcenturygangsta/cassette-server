package com.playlist.cassette.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Track extends BaseAuditEntity{

    @Id
    @GeneratedValue
    @Column(name = "track_id")
    private Long id;
    private Long tape_id;
    private String name;
    private String sender_name;
    private String file_name;
    private String audio_link;

    @Builder
    public Track(Long tape_id, String name, String sender_name, String file_name, String audio_link) {
        this.tape_id = tape_id;
        this.name = name;
        this.sender_name = sender_name;
        this.file_name = file_name;
        this.audio_link = audio_link;
    }
}
