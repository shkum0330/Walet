package com.ssafy.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ServiceApplication {
	@PostConstruct
	public void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("KST"));
	}
	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}
}
