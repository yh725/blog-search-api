package com.blog.api.controller;

import com.blog.api.service.SearchKeywordService;
import com.blog.api.service.impl.KakaoBlogSearchServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BlogSearchController.class)
class BlogSearchControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private KakaoBlogSearchServiceImpl kakaoBlogSearchService;

	@MockBean
	private SearchKeywordService searchKeywordService;


	@DisplayName("블로그 검색 : query 파라미터 필수값 테스트")
	@Test
	void blogSearchAPITest() throws Exception {

		mockMvc.perform(
						get("/blog/search/api")
				)
				.andExpect(status().isBadRequest());
	}

	@DisplayName("블로그 검색 : size 범위 테스트")
	@Test
	void blogSearchAPITest2() throws Exception {

		mockMvc.perform(
						get("/blog/search/api")
								.param("query", "테스트")
								.param("size", "5000")
				)
				.andExpect(status().isBadRequest());
	}

	@DisplayName("블로그 검색 : 정상 테스트")
	@Test
	void blogSearchAPITest3() throws Exception {

		mockMvc.perform(
						get("/blog/search/api")
								.param("query", "테스트")
				)
				.andExpect(status().isOk());
	}

	@DisplayName("인기 검색어 : size 범위 테스트")
	@Test
	void topKeywordTest() throws Exception {

		mockMvc.perform(
						get("/blog/search/api/top")
								.param("size", "20")
				)
				.andExpect(status().isBadRequest());
	}

	@DisplayName("인기 검색어 : 정상 테스트")
	@Test
	void topKeywordTest2() throws Exception {

		mockMvc.perform(
						get("/blog/search/api/top")
				)
				.andExpect(status().isOk());
	}
}