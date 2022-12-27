package com.playlist.cassette.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String email;
    private String gender;
    private String age;
    private String kakaoMemberId;

    @Builder
    public Member(String name, String email, String gender, String age, String kakaoMemberId) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.kakaoMemberId = kakaoMemberId;
    }
}
