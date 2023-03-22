package com.blog.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApiServer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "application,application-common");
		SpringApplication.run(BlogApiServer.class, args);
	}
}
