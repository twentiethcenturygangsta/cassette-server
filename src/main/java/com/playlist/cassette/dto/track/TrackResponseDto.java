package com.playlist.cassette.dto.track;

import com.playlist.cassette.entity.Track;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrackResponseDto {
    private Long tape_id;
    private String name;
    private String sender_name;
    private String file_name;
    private String audio_link;

    @Builder
    public TrackResponseDto(Track track) {
        this.tape_id = track.getTape_id();
        this.name = track.getName();
        this.sender_name = track.getSender_name();
        this.file_name = track.getFile_name();
        this.audio_link = track.getAudio_link();
    }
}
