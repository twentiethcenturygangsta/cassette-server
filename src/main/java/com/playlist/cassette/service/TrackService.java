package com.playlist.cassette.service;


import com.playlist.cassette.dto.track.TrackResponseDto;
import com.playlist.cassette.dto.track.TrackSaveRequestDto;
import com.playlist.cassette.entity.Tape;
import com.playlist.cassette.entity.Track;
import com.playlist.cassette.handler.exception.ExceptionCode;
import com.playlist.cassette.handler.exception.UserException;
import com.playlist.cassette.repository.TapeRepository;
import com.playlist.cassette.repository.TrackRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TrackService {
    private static final int MAXIMUM_SIZE_OF_TRACKS_PER_TAPE = 12;
    private final AwsS3Service awsS3Service;
    private final TapeService tapeService;
    private final TapeRepository tapeRepository;
    private final TrackRepository trackRepository;

    public TrackResponseDto getTrack(Long id) {
        Track track = trackRepository.findById(id).orElseThrow(() ->
                new UserException(ExceptionCode.NOT_FOUND_TRACKS, ExceptionCode.NOT_FOUND_TRACKS.getMessage()));

        return TrackResponseDto.builder().track(track).build();
    }

    @Transactional
    public TrackResponseDto createTrack(TrackSaveRequestDto requestDto, MultipartFile multipartFile, String dirName) throws Exception {
        Tape tape = tapeRepository.findByTapeLinkAndIsRemoved(requestDto.getTapeLink(), false).orElseThrow(() ->
                new UserException(ExceptionCode.NOT_FOUND_TAPES, ExceptionCode.NOT_FOUND_TAPES.getMessage()));

        if (is_exceed_track(tape)) {
            throw new UserException(ExceptionCode.NUMBER_OF_TRACKS_PER_TAPE_EXCEEDS_12, ExceptionCode.NUMBER_OF_TRACKS_PER_TAPE_EXCEEDS_12.getMessage());
        }
        Track track = trackRepository.save(requestDto.toEntity(tape));

        String fileName = track.getTape().getId() + "_" + track.getId() + ".wav";
        System.out.println(fileName);

        File uploadFile = convert(multipartFile, fileName).orElseThrow(() ->
                new UserException(ExceptionCode.NOT_INVALID_FILE_FORMAT, ExceptionCode.NOT_INVALID_FILE_FORMAT.getMessage()));

        String audioLink = awsS3Service.upload(uploadFile, dirName);

        track.update(fileName, audioLink);
        Track trackData = trackRepository.save(track);

        if(tape.getTracks().size() == MAXIMUM_SIZE_OF_TRACKS_PER_TAPE-1) tapeService.uploadTape(tape, dirName);

        return TrackResponseDto.builder().track(track).build();
    }

    public ResponseEntity<byte[]> downloadTrack(Long id, String dirName) throws IOException {
        TrackResponseDto track = getTrack(id);

        String fileName = dirName + "/" + track.getFileName();
        String type = fileName.substring(fileName.lastIndexOf("."));
        String downName = URLEncoder.encode(track.getName() + "'s Tape" + type, "UTF-8").replaceAll("\\+", "%20");;

        return awsS3Service.download(fileName, downName);
    }

    private Optional<File> convert(MultipartFile file, String fileName) throws IOException {
        File convertFile = new File(fileName);
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    private boolean is_exceed_track(Tape tape) {
        return tape.getTracks().size() >= MAXIMUM_SIZE_OF_TRACKS_PER_TAPE;
    }

}
