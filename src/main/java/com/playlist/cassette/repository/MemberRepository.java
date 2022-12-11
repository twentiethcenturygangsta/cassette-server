package com.playlist.cassette.repository;

import com.playlist.cassette.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
