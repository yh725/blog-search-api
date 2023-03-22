package com.blog.h2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("com.blog.core")
@SpringBootApplication
public class BlogH2Server {

	public static void main(String[] args) {
		SpringApplication.run(BlogH2Server.class, args);
	}
}
