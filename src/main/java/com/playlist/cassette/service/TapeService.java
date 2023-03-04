package com.playlist.cassette.service;

import com.playlist.cassette.dto.tape.*;
import com.playlist.cassette.dto.track.TrackResponseDto;
import com.playlist.cassette.entity.Member;
import com.playlist.cassette.entity.Tape;
import com.playlist.cassette.handler.exception.ExceptionCode;
import com.playlist.cassette.handler.exception.UserException;
import com.playlist.cassette.repository.MemberRepository;
import com.playlist.cassette.repository.TapeRepository;
import com.playlist.cassette.repository.TrackRepository;
import com.playlist.cassette.utils.RemoveFileUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.net.URL;
import java.net.URLEncoder;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TapeService {

    private final int NUMBER_OF_TAPE_PER_MEMBER_EXCEEDS = 1;
    private final int FIRST_BASE_FILE_INDEX = 1;
    private final String MERGE_TAPE_FILE_NAME = "Tape_Ver_";
    private final AwsS3Service awsS3Service;
    private final TapeRepository tapeRepository;
    private final MemberRepository memberRepository;
    private final TrackRepository trackRepository;

    public List<TapeListResponseDto> getTapes(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new UserException(ExceptionCode.INVALID_MEMBER, ExceptionCode.INVALID_MEMBER.getMessage()));

        return tapeRepository.findTapesByMember(member).stream().map(TapeListResponseDto::new).collect(Collectors.toList());
    }

    public TapeGuestResponseDto getTape(String tapeLink) {
        Tape tape = tapeRepository.findByTapeLinkAndIsRemoved(tapeLink, false).orElseThrow(() ->
                new UserException(ExceptionCode.NOT_FOUND_TAPES, ExceptionCode.NOT_FOUND_TAPES.getMessage()));

        return TapeGuestResponseDto.builder().tape(tape).build();
    }

    public TapeResponseDto createTape(Long memberId, TapeSaveRequestDto requestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new UserException(ExceptionCode.INVALID_MEMBER, ExceptionCode.INVALID_MEMBER.getMessage()));

        if (is_exceed_tape(member)) {
            throw new UserException(ExceptionCode.NUMBER_OF_TAPE_PER_MEMBER_EXCEEDS_1, ExceptionCode.NUMBER_OF_TAPE_PER_MEMBER_EXCEEDS_1.getMessage());
        }
        Tape tape = tapeRepository.save(requestDto.toEntity(member));

        return TapeResponseDto.builder().tape(tape).build();
    }

    public TapeResponseDto updateTape(Long tapeId, TapeUpdateRequestDto requestDto) {
        Tape tape = tapeRepository.findById(tapeId).orElseThrow(() ->
                new UserException(ExceptionCode.NOT_FOUND_TAPES, ExceptionCode.NOT_FOUND_TAPES.getMessage()));

        tape.update(requestDto.getColorCode(), requestDto.getTitle(), requestDto.getName());
        tapeRepository.save(tape);

        return TapeResponseDto.builder().tape(tape).build();
    }

    public ResponseEntity<byte[]> downloadTape(Long tapeId, String dirName) throws IOException {
        Tape tape = tapeRepository.findById(tapeId).orElseThrow(() ->
                new UserException(ExceptionCode.NOT_FOUND_TAPES, ExceptionCode.NOT_FOUND_TAPES.getMessage()));

        String fileName = dirName + "/" + tape.getFileName();
        String type = fileName.substring(fileName.lastIndexOf("."));
        String downName = URLEncoder.encode(tape.getMember().getName() + "'s Tape" + type, "UTF-8").replaceAll("\\+", "%20");;

        return awsS3Service.download(fileName, downName);
    }

    public void uploadTape(Tape tape, String dirName) throws IOException {
        String folderPath = "src/main/" + tape.getId() + "TapeFolder";
        File folder = new File(folderPath);
        if(!folder.exists()) folder.mkdir();

        downloadTracks(tape, folderPath);

        File uploadFile = new File(mergeTrack(folderPath, tape.getId()));
        String audioLink = awsS3Service.upload(uploadFile, dirName);

        tape.updateAudioLink(uploadFile.getName(), audioLink);
        tapeRepository.save(tape);
        RemoveFileUtils.removeFile(folder);
    }

    private void downloadTracks(Tape tape, String folderPath) throws IOException{
        List<TrackResponseDto> tracks = trackRepository.findTrackByTape(tape).stream().map(TrackResponseDto::new).collect(Collectors.toList());

        for(int i=0; i<tracks.size(); i++) {
            TrackResponseDto track = tracks.get(i);
            String fileName = folderPath + "/" + ("A".repeat(i+1)) + ".wav";
            String audioLink = track.getAudioLink();

            File trackFile = new File(fileName);
            FileUtils.copyURLToFile(new URL(audioLink), trackFile);
        }
    }

    private String mergeTrack(String folderPath, Long tapeId) {
        String tapeLink = folderPath + "/Tape_" + tapeId + ".wav";

        try {
            File file = new File(folderPath);
            File[] fileList = file.listFiles();
            Arrays.sort(fileList, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return o1.getName().length() - o2.getName().length();
                }
            });

            AudioInputStream[] clip = new AudioInputStream[fileList.length];
            for(int i=0; i<fileList.length; i++) {
                clip[i] = AudioSystem.getAudioInputStream(new File(folderPath + "/" + fileList[i].getName()));
            }

            for(int i=1; i<fileList.length; i++) {
                if(i == FIRST_BASE_FILE_INDEX) {
                    AudioInputStream appendedFiles =
                            new AudioInputStream(
                                    new SequenceInputStream(clip[i-1], clip[i]),
                                    clip[i].getFormat(),clip[i-1].getFrameLength() + clip[i].getFrameLength());

                    AudioSystem.write(appendedFiles, AudioFileFormat.Type.WAVE, new File(folderPath + "/" + MERGE_TAPE_FILE_NAME + i + ".wav"));
                    RemoveFileUtils.removeFile(fileList[i-1]);
                    RemoveFileUtils.removeFile(fileList[i]);
                } else {
                    AudioInputStream saveClip = AudioSystem.getAudioInputStream(new File(folderPath + "/" + MERGE_TAPE_FILE_NAME + (i-1) + ".wav"));

                    AudioInputStream appendedFiles =
                            new AudioInputStream(
                                    new SequenceInputStream(saveClip, clip[i]),
                                    saveClip.getFormat(),saveClip.getFrameLength() + clip[i].getFrameLength());

                    if(i == fileList.length-1) {
                        AudioSystem.write(appendedFiles, AudioFileFormat.Type.WAVE, new File(tapeLink));
                    } else {
                        AudioSystem.write(appendedFiles, AudioFileFormat.Type.WAVE, new File(folderPath + "/" + MERGE_TAPE_FILE_NAME + i + ".wav"));
                    }
                    RemoveFileUtils.removeFile(fileList[i]);
                    RemoveFileUtils.removeFile(new File(folderPath + "/" + MERGE_TAPE_FILE_NAME + (i-1) + ".wav"));
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return tapeLink;
    }

    private void mergeTrackFiles() {

    }

    private boolean is_exceed_tape(Member member) {
        return member.getTapes().size() >= NUMBER_OF_TAPE_PER_MEMBER_EXCEEDS;
    }
}
