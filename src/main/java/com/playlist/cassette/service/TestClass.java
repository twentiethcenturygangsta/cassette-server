package com.playlist.cassette.service;

import com.twentiethcenturygangsta.ourboard.auth.UserCredentials;
import com.twentiethcenturygangsta.ourboard.auth.UserDatabaseCredentials;
import com.twentiethcenturygangsta.ourboard.site.OurBoardClient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class TestClass {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${secretKey}")
    private String secretKty;

    @Bean
    public OurBoardClient jamBoardClient() throws Exception {
        UserDatabaseCredentials userDatabaseCredentials = new UserDatabaseCredentials(
                url,
                userName,
                password
        );
        UserCredentials userCredentials = new UserCredentials(
                secretKty,
                "test",
                "test1234"
        );

        return OurBoardClient.builder()
                .userDatabaseCredentials(userDatabaseCredentials)
                .userCredentials(userCredentials)
                .build();
    }
}
