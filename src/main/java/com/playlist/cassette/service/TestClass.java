package com.playlist.cassette.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.playlist.cassette.entity.Member;
import com.playlist.cassette.entity.Tape;
import com.playlist.cassette.entity.Track;
import com.twentiethcenturygangsta.jamboard.auth.Role;
import com.twentiethcenturygangsta.jamboard.auth.UserCredentials;
import com.twentiethcenturygangsta.jamboard.controller.AdminController;
import com.twentiethcenturygangsta.jamboard.database.UserDatabaseCredentials;
import com.twentiethcenturygangsta.jamboard.site.JamBoardClient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@Configuration
public class TestClass {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public JamBoardClient jamBoardClient() throws SQLException {
        UserDatabaseCredentials userDatabaseCredentials = new UserDatabaseCredentials(
                url,
                userName,
                password
        );
        UserCredentials userCredentials = new UserCredentials(
                "test",
                "test1234",
                Role.SUPER_USER
        );

        return JamBoardClient.builder()
                .userDatabaseCredentials(userDatabaseCredentials)
                .userCredentials(userCredentials)
                .basePackagePath("com.playlist.cassette")
                .build();
    }
}
