package com.playlist.cassette.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.playlist.cassette.dto.member.MemberResponseDto;
import com.playlist.cassette.dto.track.TrackResponseDto;
import com.playlist.cassette.dto.track.TrackSaveRequestDto;
import com.playlist.cassette.entity.Member;
import com.playlist.cassette.entity.Track;
import com.playlist.cassette.handler.exception.ExceptionCode;
import com.playlist.cassette.handler.exception.UserException;
import com.playlist.cassette.handler.response.ResponseHandler;
import com.playlist.cassette.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class TrackService {

    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final TrackRepository trackRepository;

    public TrackResponseDto getTrack(Long id) {
        Track track = trackRepository.findById(id).orElseThrow(() ->
                new UserException(ExceptionCode.NOT_FOUND_TRACKS, ExceptionCode.NOT_FOUND_TRACKS.getMessage()));

        return TrackResponseDto.builder().track(track).build();
    }

    public TrackResponseDto createTrack(TrackSaveRequestDto requestDto, MultipartFile multipartFile, String dirName) throws IOException {
        Track track = trackRepository.save(requestDto.toEntity());

        String type = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        String fileName = track.getTapeId() + "_" + track.getId() + type;

        File uploadFile = convert(multipartFile, fileName).orElseThrow(() ->
                new UserException(ExceptionCode.NOT_INVALID_FILE_FORMAT, ExceptionCode.NOT_INVALID_FILE_FORMAT.getMessage()));

        String audioLink = upload(uploadFile, dirName);

        track.update(fileName, audioLink);
        trackRepository.save(track);

        return TrackResponseDto.builder().track(track).build();
    }

    public ResponseEntity<byte[]> downloadTrack(Long id, String dirName) throws IOException {
        TrackResponseDto track = getTrack(id);

        String fileName = dirName + "/" + track.getFileName();
        String type = fileName.substring(fileName.lastIndexOf("."));
        String downName = URLEncoder.encode(track.getSenderName() + "'s Tape" + type, "UTF-8").replaceAll("\\+", "%20");;

        S3Object s3Object = amazonS3Client.getObject(new GetObjectRequest(bucket, fileName));
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectInputStream);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);

        httpHeaders.setContentDispositionFormData("attachment", downName);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadFileUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadFileUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
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

}
