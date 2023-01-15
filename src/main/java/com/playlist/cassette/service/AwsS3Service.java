package com.playlist.cassette.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;

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
@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadFileUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadFileUrl;
    }

    public ResponseEntity<byte[]> download(String fileName, String downName) throws IOException {
        S3Object s3Object = amazonS3Client.getObject(new GetObjectRequest(bucket, fileName));
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectInputStream);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);

        httpHeaders.setContentDispositionFormData("attachment", downName);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info(targetFile.getName() + " 파일이 삭제되었습니다.");
        } else {
            log.info(targetFile.getName() + " 파일이 삭제되지 못했습니다.");
        }
    }
}
