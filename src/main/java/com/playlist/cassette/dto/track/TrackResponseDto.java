package com.playlist.cassette.dto.track;

import com.playlist.cassette.entity.Track;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrackResponseDto {
    private Long trackId;
    private Long tapeId;
    private String colorCode;
    private String title;
    private String name;
    private String fileName;
    private String audioLink;

    @Builder
    public TrackResponseDto(Track track) {
        this.trackId = track.getId();
        this.tapeId = track.getTape().getId();
        this.colorCode = track.getColorCode();
        this.title = track.getTitle();
        this.name = track.getName();
        this.fileName = track.getFileName();
        this.audioLink = track.getAudioLink();
    }
}
