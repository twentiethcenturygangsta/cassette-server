package com.playlist.cassette.dto.track;

import com.playlist.cassette.entity.Track;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrackSaveRequestDto {
    private String tape_id;
    private String name;
    private String sender_name;
    private String file_name;

    @Builder
    public TrackSaveRequestDto(String tape_id, String name, String sender_name, String file_name) {
        this.tape_id = tape_id;
        this.name = name;
        this.sender_name = sender_name;
        this.file_name = file_name;
    }

    public Track toEntity() {
        return Track.builder()
                .tape_id(Long.valueOf(tape_id))
                .name(name)
                .sender_name(sender_name)
                .file_name(file_name)
                .build();
    }
}
