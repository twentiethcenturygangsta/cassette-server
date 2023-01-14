package com.playlist.cassette.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseAuditEntity{

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String email;
    private String gender;
    private String age;
    private Long kakaoMemberId;
    private String refreshToken;
    private Date refreshTokenExpireTime;

    @Builder
    public Member(String name, String email, String gender, String age, Long kakaoMemberId) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.kakaoMemberId = kakaoMemberId;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        this.refreshTokenExpireTime = new Date();
    }
}
