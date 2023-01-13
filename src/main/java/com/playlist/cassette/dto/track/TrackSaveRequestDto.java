package com.playlist.cassette.dto.track;

import com.playlist.cassette.entity.Track;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrackSaveRequestDto {
    private String tapeId;
    private String name;
    private String senderName;
    private String fileName;

    @Builder
    public TrackSaveRequestDto(String tapeId, String name, String senderName, String fileName) {
        this.tapeId = tapeId;
        this.name = name;
        this.senderName = senderName;
        this.fileName = fileName;
    }

    public Track toEntity() {
        return Track.builder()
                .tapeId(Long.valueOf(tapeId))
                .name(name)
                .senderName(senderName)
                .fileName(fileName)
                .build();
    }
}
