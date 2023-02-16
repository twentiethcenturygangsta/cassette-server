package com.playlist.cassette.service;

import com.twentiethcenturygangsta.ourboard.auth.Role;
import com.twentiethcenturygangsta.ourboard.auth.UserCredentials;
import com.twentiethcenturygangsta.ourboard.database.UserDatabaseCredentials;
import com.twentiethcenturygangsta.ourboard.site.OurBoardClient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;


@Slf4j
@Configuration
public class TestClass {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public OurBoardClient jamBoardClient() throws SQLException {
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

//        String[] beans = applicationContext.getBeanDefinitionNames();

//        for (String bean : beans) {
//            log.info("bean = {}" + bean);
//        }


        return OurBoardClient.builder()
                .userDatabaseCredentials(userDatabaseCredentials)
                .userCredentials(userCredentials)
                .basePackagePath("com.playlist.cassette")
                .build();
    }
}
