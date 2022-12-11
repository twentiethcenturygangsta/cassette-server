package com.playlist.cassette.repository;

import com.playlist.cassette.entity.Tape;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TapeRepository extends JpaRepository<Tape, Long> {
}
