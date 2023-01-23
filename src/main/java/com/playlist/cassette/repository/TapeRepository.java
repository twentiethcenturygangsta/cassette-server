package com.playlist.cassette.repository;

import com.playlist.cassette.entity.Member;
import com.playlist.cassette.entity.Tape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TapeRepository extends JpaRepository<Tape, Long> {
    Optional<Tape> findTapeByMember(Member member);
    List<Tape> findTapesByMember(Member member);
    Optional<Tape> findByTapeLinkAndIsRemoved(String tapeLink, boolean isRemoved);
}
