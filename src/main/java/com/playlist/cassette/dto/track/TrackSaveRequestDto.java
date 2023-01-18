package com.playlist.cassette.dto.track;

import com.playlist.cassette.annotation.ValidName;
import com.playlist.cassette.annotation.ValidTitle;
import com.playlist.cassette.entity.Tape;
import com.playlist.cassette.entity.Track;
import com.playlist.cassette.handler.exception.ExceptionCode;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrackSaveRequestDto {
    private String uuid;
    private String colorCode;
    @ValidTitle(exceptionCode = ExceptionCode.NOT_INVALID_TAPE_TITLE)
    private String title;
    @ValidName(exceptionCode = ExceptionCode.NOT_INVALID_TAPE_NAME)
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
