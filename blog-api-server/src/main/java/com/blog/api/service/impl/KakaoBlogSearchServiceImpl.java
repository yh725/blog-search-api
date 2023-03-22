package com.blog.api.service.impl;

import com.blog.api.dto.BlogSearchRequestDto;
import com.blog.api.dto.KakaoDto;
import com.blog.api.dto.NaverDto;
import com.blog.api.service.BlogSearchService;
import com.blog.api.service.SearchKeywordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoBlogSearchServiceImpl implements BlogSearchService {

	@Value("${kakao.url}")
	String url;

	@Value("${kakao.key}")
	String key;

	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	private final SearchKeywordService searchKeywordService;

	private final NaverBlogSearchServiceImpl naverBlogSearchService;

	@Override
	public Map<String, Object> BlogSearch(BlogSearchRequestDto blogSearchDto) {

		//키워드 검색 저장
		searchKeywordService.searchKeywordSave(blogSearchDto.getQuery());

		restTemplate.getMessageConverters()
				.add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", "KakaoAK " + key);

		HttpEntity<?> entity = new HttpEntity<>(header);
		URI uri = UriComponentsBuilder
				.fromUriString(url)
				.queryParam("query", blogSearchDto.getQuery())
				.queryParam("sort", blogSearchDto.getSort())
				.queryParam("page", blogSearchDto.getPage())
				.queryParam("size", blogSearchDto.getSize())
				.build()
				.encode(StandardCharsets.UTF_8)
				.toUri();

		ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

		HttpStatus statusCode = exchange.getStatusCode();   //상태코드확인
		HttpHeaders headers = exchange.getHeaders();    //헤더정보확인
		String body = exchange.getBody();   //바디정보확인

		Map<String, Object> result = new HashMap<>();

		//카카오 API 응답이 정상이 아닐 시 Naver API 사용
		if (statusCode != HttpStatus.OK) {
			NaverDto naverDto = naverBlogSearchService.BlogSearch(blogSearchDto);
			result.put("NaverBlog", naverDto.getItems());
			result.put("totalCount", naverDto.getTotal());
		} else {
			try {
				KakaoDto kakaoDto = objectMapper.readValue(body, KakaoDto.class);
				result.put("KakaoBlog", kakaoDto.getDocuments());
				result.put("totalCount", kakaoDto.getMeta().getPageable_count());
			} catch (JsonProcessingException e) {
				throw new RuntimeException("카카오 JSON 처리 오류");
			}
		}

		return result;
	}
}
