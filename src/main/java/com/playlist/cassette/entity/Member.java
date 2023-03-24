package com.playlist.cassette.entity;

import com.playlist.cassette.dto.auth.TokenDto;
import com.github.twentiethcenturygangsta.ourboard.annoatation.OurBoardEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@OurBoardEntity(group="cassette", description = "Member 정보")
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
    private SocialLoginType socialLoginType;
    private Date refreshTokenExpireTime;

    @OneToMany(mappedBy = "member")
    private List<Tape> tapes = new ArrayList<>();

    @Builder
    public Member(String name, String email, String gender, String age, Long kakaoMemberId) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.kakaoMemberId = kakaoMemberId;
    }

    public void updateRefreshToken(TokenDto refreshToken) {
        this.refreshToken = refreshToken.getValue();
        this.refreshTokenExpireTime = refreshToken.getExpiredTime();
    }
}
