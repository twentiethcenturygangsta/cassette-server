package com.playlist.cassette.config.security;

import com.playlist.cassette.config.security.jwt.JwtAuthenticationEntryPoint;
import com.playlist.cassette.config.security.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtTokenFilter jwtTokenFilter;

    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/callback",
            "/api/v1/track",
            "/api/v1/tape/{uuid}/guest"
    };
    private static final String[] AUTH_CONFIRM_LIST = {
            "/api/v1/track/{id}",
            "/api/v1/track/download/{id}",
            "/api/v1/member/**",
            "/api/v1/tape",
            "/api/v1/tape/{id}",
            "/api/v1/tape/download/{id}",
            "/api/v1/auth/**"
    };

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(
            HttpSecurity http
    ) throws Exception {
        return http
                .cors().and()
                .csrf().disable() //csrf
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint).and()// 예외처리
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(
                        jwtTokenFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .authorizeHttpRequests()
                .requestMatchers(AUTH_CONFIRM_LIST).authenticated()
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .and()
                .build(); // 권한 설정
    }
}
