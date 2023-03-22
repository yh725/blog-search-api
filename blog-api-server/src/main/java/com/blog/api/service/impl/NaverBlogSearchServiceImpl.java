package com.blog.api.service.impl;

import com.blog.api.dto.BlogSearchRequestDto;
import com.blog.api.dto.NaverDto;
import com.blog.api.service.BlogSearchService;
import com.blog.api.service.SearchKeywordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class NaverBlogSearchServiceImpl implements BlogSearchService {

	@Value("${naver.url}")
	String url;

	@Value("${naver.id}")
	String id;

	@Value("${naver.key}")
	String key;

	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	private final SearchKeywordService searchKeywordService;

	@Override
	public NaverDto BlogSearch(BlogSearchRequestDto blogSearchDto) {

		//키워드 검색 저장
		searchKeywordService.searchKeywordSave(blogSearchDto.getQuery());

		//whe
		restTemplate.getMessageConverters()
				.add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

		HttpHeaders header = new HttpHeaders();
		header.set("X-Naver-Client-Id", id);
		header.set("X-Naver-Client-Secret", key);

		HttpEntity<?> entity = new HttpEntity<>(header);
		URI uri = UriComponentsBuilder
				.fromUriString(url)
				.queryParam("query", blogSearchDto.getQuery())
				.queryParam("sort", "recency".equals(blogSearchDto.getSort()) ? "date" : "sim")
				.queryParam("start", blogSearchDto.getPage())
				.queryParam("display", blogSearchDto.getSize())
				.build()
				.encode(StandardCharsets.UTF_8)
				.toUri();

		ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

		HttpStatus statusCode = exchange.getStatusCode();   //상태코드확인

		if (statusCode != HttpStatus.OK) {
			throw new RuntimeException("API 장애");
		}
		HttpHeaders headers = exchange.getHeaders();    //헤더정보확인
		String body = exchange.getBody().toString();   //바디정보확인

		NaverDto naverDto;

		try {
			naverDto = objectMapper.readValue(body, NaverDto.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("네이버 JSON 처리 오류");
		}

		return naverDto;
	}
}
