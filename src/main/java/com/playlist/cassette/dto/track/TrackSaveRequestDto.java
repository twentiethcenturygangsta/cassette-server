package com.playlist.cassette.dto.track;

import com.playlist.cassette.entity.Tape;
import com.playlist.cassette.entity.Track;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrackSaveRequestDto {
    private String uuid;
    private String colorCode;
    private String title;
    private String name;

    @Builder
    public TrackSaveRequestDto(String uuid, String colorCode, String title, String name) {
        this.uuid = uuid;
        this.colorCode = colorCode;
        this.title = title;
        this.name = name;
    }

    public Track toEntity(Tape tape) {
        return Track.builder()
                .tape(tape)
                .colorCode(colorCode)
                .title(title)
                .name(name)
                .build();
    }
}
