package com.playlist.cassette.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:3000",
                        "https://www.12playlist.com",
                        "https://ganstatest.12playlist.com",
                        "https://www.ganstatest.12playlist.com",
                        "https://12playlist.com",
                        "https://www.gangstatest.12playlist.com",
                        "https://gangstatest.12playlist.com",
                        "https://api.12playlist.com",
                        "https://cassette-git-develop-kimkyungmin123.vercel.app"
                )
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("refreshToken")
                .allowCredentials(true);
    }
}
