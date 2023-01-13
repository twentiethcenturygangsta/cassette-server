package com.playlist.cassette.dto.track;

import com.playlist.cassette.entity.Track;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrackResponseDto {
    private Long tapeId;
    private String name;
    private String senderName;
    private String fileName;
    private String audioLink;

    @Builder
    public TrackResponseDto(Track track) {
        this.tapeId = track.getTapeId();
        this.name = track.getName();
        this.senderName = track.getSenderName();
        this.fileName = track.getFileName();
        this.audioLink = track.getAudioLink();
    }
}
