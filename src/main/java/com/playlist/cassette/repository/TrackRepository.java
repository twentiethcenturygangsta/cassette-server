package com.playlist.cassette.repository;

import com.playlist.cassette.entity.Tape;
import com.playlist.cassette.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    List<Track> findTrackByTape(Tape tape);
}
