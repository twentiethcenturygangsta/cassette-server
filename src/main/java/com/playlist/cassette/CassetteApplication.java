package com.playlist.cassette;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import java.util.TimeZone;


@SpringBootApplication
@EnableFeignClients
public class CassetteApplication {

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	public static void main(String[] args) {
		String profile = System.getProperty("spring.profiles.active");
		if (StringUtils.isBlank(profile)) {
			profile = "local";
		}
		System.setProperty("spring.profiles.active", profile);
		SpringApplication.run(CassetteApplication.class, args);
	}

}
