package com.blog.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BlogBatchServer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "application,application-common");
		SpringApplication.run(BlogBatchServer.class, args);
	}
}
