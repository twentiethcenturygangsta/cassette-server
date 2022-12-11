package com.playlist.cassette.repository;

import com.playlist.cassette.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long> {
}
