package com.blog.api;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer {

	@Bean
	public RestTemplate buildRestTemplate() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

		CloseableHttpClient httpClient = HttpClientBuilder.create()
				.setMaxConnTotal(100) //최대 커넥션 수
				.setMaxConnPerRoute(5)  //각 호스트(IP와 Port의 조합)당 커넥션 풀에 생성가능한 커넥션 수
				.build();

		factory.setHttpClient(httpClient);
		factory.setConnectTimeout(5000); // connection timeout
		factory.setReadTimeout(5000); // read timeout

		return new RestTemplate(factory);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		return mapper;
	}
}
