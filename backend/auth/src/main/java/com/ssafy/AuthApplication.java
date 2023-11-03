package com.ssafy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AuthApplication {

	public static void main(String[] args) {
		System.setProperty("http.proxyHost", "121.162.108.65");
		System.setProperty("http.proxyPort", "9460");
		SpringApplication.run(AuthApplication.class, args);
	}
}
