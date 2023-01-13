package com.playlist.cassette.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class Color extends BaseAuditEntity {
    @Id
    @GeneratedValue
    @Column(name = "color_id")
    private Long id;
    private String code;

    @Builder
    public Color(String code) {
        this.code = code;
    }
}
