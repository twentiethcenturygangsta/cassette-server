package com.playlist.cassette.repository;

import com.playlist.cassette.dto.track.TrackResponseDto;
import com.playlist.cassette.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
}
