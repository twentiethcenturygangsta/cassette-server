package com.playlist.cassette.entity;

import com.playlist.cassette.init.InitMember;
import com.playlist.cassette.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    InitMember initMember = new InitMember();
    Member member = initMember.createDefaultMember();

    @BeforeEach
    void setup() {
        memberRepository.save(member);
    }

    @AfterEach
    void afterSetup() {
        memberRepository.delete(member);
    }

    @Nested
    @DisplayName("member 객체의 상속필드 테스트")
    class TestMember {

        @Test
        @DisplayName("Member 객체에 createAt 값이 정상적으로 들어간다.")
        public void test_created_at_in_member_성공() {
            Optional<Member> savedMember = memberRepository.findByKakaoMemberId(1L);
            savedMember.ifPresent(presentedMember -> {
                assertThat(presentedMember.getCreatedAt()).isNotNull();
            });
        }

        @Test
        @DisplayName("Member 객체에 lastModifiedAt 값이 정상적으로 들어간다.")
        public void test_last_modified_at_in_member_성공() {
            Optional<Member> savedMember = memberRepository.findByKakaoMemberId(1L);
            savedMember.ifPresent(presentedMember -> {
                assertThat(presentedMember.getLastModifiedAt()).isNotNull();
            });
        }

        @Test
        @DisplayName("Member 객체에 초기 isRemoved 값은 false로 정상적으로 들어간다.")
        public void test_is_removed_in_member_성공() {
            Optional<Member> savedMember = memberRepository.findByKakaoMemberId(1L);
            savedMember.ifPresent(presentedMember -> {
                assertThat(presentedMember.getIsRemoved()).isEqualTo(false);
            });
        }
    }
}
