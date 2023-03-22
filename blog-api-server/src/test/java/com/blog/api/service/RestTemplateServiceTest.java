package com.blog.api.service;

import com.blog.api.dto.KakaoDto;
import com.blog.api.dto.NaverDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
class RestTemplateServiceTest {

//	@Autowired
//	RestTemplate restTemplate;

//	@Value("${kakao.key}")
//	private String key;

	ObjectMapper objectMapper = new ObjectMapper();

	RestTemplate restTemplate = new RestTemplate();

	@DisplayName("카카오 API 테스트")
	@Test
	public void kakaoApiTest() throws Exception {
		String key = "75b1b7f2419513246dfd022acbd3296e";
		String url = "https://dapi.kakao.com/v2/search/blog";

		restTemplate.getMessageConverters()
				.add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));// 추가한 부분

		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", "KakaoAK " + key);

		HttpEntity<?> entity = new HttpEntity<>(header);
		URI uri = UriComponentsBuilder
				.fromUriString(url)
				.queryParam("query", "검색")
				.build()
				.encode(StandardCharsets.UTF_8)
				.toUri();

		ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

		HttpStatus statusCode = exchange.getStatusCode();   //상태코드확인
		HttpHeaders headers = exchange.getHeaders();    //헤더정보확인
		String body = exchange.getBody();   //바디정보확인

		KakaoDto json = objectMapper.readValue(body, KakaoDto.class);

		assertThat(statusCode).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("네이버 API 테스트")
	@Test
	public void naverApiTest() throws Exception {
		String id = "bxmQhdmAdoknVEFheJpW";
		String key = "p3GTmdEVyx";
		String url = "https://openapi.naver.com/v1/search/blog.json";

		restTemplate.getMessageConverters()
				.add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));// 추가한 부분

		HttpHeaders header = new HttpHeaders();
		header.set("X-Naver-Client-Id", id);
		header.set("X-Naver-Client-Secret", key);

		HttpEntity<?> entity = new HttpEntity<>(header);
		URI uri = UriComponentsBuilder
				.fromUriString(url)
				.queryParam("query", "검색")
				.build()
				.encode(StandardCharsets.UTF_8)
				.toUri();

		ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

		HttpStatus statusCode = exchange.getStatusCode();   //상태코드확인
		HttpHeaders headers = exchange.getHeaders();    //헤더정보확인
		String body = exchange.getBody();   //바디정보확인

		NaverDto json = objectMapper.readValue(body, NaverDto.class);

		assertThat(statusCode).isEqualTo(HttpStatus.OK);
	}
}
