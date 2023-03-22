package com.blog.api.controller;

import com.blog.api.dto.*;
import com.blog.api.service.SearchKeywordService;
import com.blog.api.service.impl.KakaoBlogSearchServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/blog/search/api")
@RequiredArgsConstructor
public class BlogSearchController {

	private final KakaoBlogSearchServiceImpl kakaoBlogSearchService;
	private final SearchKeywordService searchKeywordService;

	@Operation(summary = "블로그 검색 API", description = "query를 이용하여 블로그를 검색합니다.")
	@GetMapping
	public ResponseEntity<BlogSearchResponseDto> blogSearchApi(@Valid @ModelAttribute BlogSearchRequestDto blogSearchRequestDto) throws Exception {

		Map<String, Object> responseData = kakaoBlogSearchService.BlogSearch(blogSearchRequestDto);
		int totalCount = getTotalCount(responseData);

		List<SearchKeywordTopDto> searchKeywordTop = searchKeywordService.findTopKeyword();
		setTopKeyword(responseData, searchKeywordTop);

		BlogSearchResponseDto blogSearchResponseDto = BlogSearchResponseDto.builder()
				.data(responseData)
				.currentPage(blogSearchRequestDto.getPage())
				.size(blogSearchRequestDto.getSize())
				.totalPages(getTotalPages(totalCount, blogSearchRequestDto.getSize()))
				.build();

		return new ResponseEntity<>(blogSearchResponseDto, HttpStatus.OK);
	}

	@Operation(summary = "인기 검색어 조회 API", description = "size를 조절하여 최대 10개까지 조회합니다.")
	@GetMapping("/top")
	public ResponseEntity<BaseResponseDto> blogTopKeyword(@Valid @ModelAttribute TopKeywordRequestDto topKeywordRequestDto) throws Exception {

		Map<String, Object> responseData = new HashMap<>();
		List<SearchKeywordTopDto> searchKeywordTop = searchKeywordService.findTopKeywordLimit(topKeywordRequestDto.getSize());
		setTopKeyword(responseData, searchKeywordTop);

		BaseResponseDto baseResponseDto = BaseResponseDto.builder()
				.data(responseData)
				.build();

		return new ResponseEntity<>(baseResponseDto, HttpStatus.OK);
	}

	private static void setTopKeyword(Map<String, Object> responseData, List<SearchKeywordTopDto> searchKeywordTop) {
		if (searchKeywordTop.size() > 0) {
			responseData.put("topKeyword", searchKeywordTop);
		}
	}

	private static int getTotalCount(Map<String, Object> responseData) {
		int totalCount = responseData.get("totalCount") == null ? 0 : Integer.parseInt(responseData.get("totalCount").toString());
		responseData.remove("totalCount");
		return totalCount;
	}

	private static int getTotalPages(int totalCount, int size) {
		int totalPages = totalCount / size;
		if (totalCount % size > 0) totalPages += 1;
		return totalPages;
	}
}
