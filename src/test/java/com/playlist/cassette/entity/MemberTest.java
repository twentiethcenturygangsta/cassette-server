package com.playlist.cassette.entity;

import com.playlist.cassette.dto.auth.TokenDto;
import com.playlist.cassette.init.InitMember;
import com.playlist.cassette.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
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

    @Test
    @DisplayName("멤버 객체 생성 성공")
    @Transactional
    public void test_create_member_성공() {
        Optional<Member> savedMember = memberRepository.findByKakaoMemberId(1L);
        savedMember.ifPresent(presentedMember -> {
            assertThat(presentedMember.getId()).isEqualTo(1);
            assertThat(presentedMember.getName()).isEqualTo("김중앙");
            assertThat(presentedMember.getEmail()).isEqualTo("test1@cassette.com");
            assertThat(presentedMember.getRefreshToken()).isNull();
            assertThat(presentedMember.getRefreshTokenExpireTime()).isNull();
            assertThat(presentedMember.getAge()).isEqualTo("20-29");
            assertThat(presentedMember.getGender()).isEqualTo("male");
            assertThat(presentedMember.getTapes()).isEqualTo(member.getTapes());
            assertThat(presentedMember.getKakaoMemberId()).isEqualTo(1L);
        });
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

    @Nested
    @DisplayName("member 객체의 refreshToken, expiredTime update 테스트")
    class TestMemberUpdateRefreshToken {
        @Test
        @DisplayName("refreshToken, expiredTime이 수정이 된다.")
        public void test_update_refresh_token_in_member_성공() {
            Date expiredTime = new Date();

            Optional<Member> savedMember = memberRepository.findByKakaoMemberId(1L);
            TokenDto tokenDto = TokenDto.builder()
                    .value("abcd")
                    .expiredTime(expiredTime)
                    .build();

            savedMember.ifPresent(presentedMember -> {
                presentedMember.updateRefreshToken(tokenDto);
                assertThat(presentedMember.getRefreshToken()).isEqualTo(tokenDto.getValue());
                assertThat(presentedMember.getRefreshTokenExpireTime()).isEqualTo(tokenDto.getExpiredTime());
            });
        }
    }
}
