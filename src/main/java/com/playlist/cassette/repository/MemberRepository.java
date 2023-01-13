package com.playlist.cassette.repository;

import com.playlist.cassette.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByKakaoMemberId(Long id);
    Optional<Member> findByKakaoMemberId(Long id);
    Optional<Member> findByRefreshToken(String refreshToken);
}
