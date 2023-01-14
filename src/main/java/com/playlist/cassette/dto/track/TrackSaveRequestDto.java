package com.playlist.cassette.dto.track;

import com.playlist.cassette.entity.Tape;
import com.playlist.cassette.entity.Track;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrackSaveRequestDto {
    private Long tapeId;
    private String colorCode;
    private String name;
    private String senderName;

    @Builder
    public TrackSaveRequestDto(Long tapeId, String colorCode, String name, String senderName) {
        this.tapeId = tapeId;
        this.colorCode = colorCode;
        this.name = name;
        this.senderName = senderName;
    }

    public Track toEntity(Tape tape) {
        return Track.builder()
                .tape(tape)
                .colorCode(colorCode)
                .name(name)
                .senderName(senderName)
                .build();
    }
}
