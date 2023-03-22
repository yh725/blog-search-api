package com.blog.api.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles({"common"})
@Slf4j
class SearchKeywordServiceTest {

	@Autowired
	SearchKeywordService searchKeywordService;

	@Test
	@DisplayName("검색 동시성 테스트")
	void increaseTest() throws InterruptedException {
		int numberOfThreads = 50;
		String keyword = "검색동시성테스트";
		searchKeywordService.searchKeywordSave(keyword); // 최초 데이터 저장
		Long beforeCount = searchKeywordService.findSearchKeywordCount(keyword);
		ExecutorService service = Executors.newFixedThreadPool(50);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);

		for (int i = 0; i < numberOfThreads; i++) {
			service.execute(() -> {
				searchKeywordService.searchKeywordSave(keyword);
				latch.countDown();
			});
		}
		latch.await();

		Long afterCount = searchKeywordService.findSearchKeywordCount(keyword);
		assertThat(afterCount).isEqualTo(beforeCount + 50);
	}

	@Test
	@Rollback
	@DisplayName("인기 검색어 조회 테스트")
	void topKeywordTest() {
		for (int i = 0; i < 10; i++) {
			searchKeywordService.searchKeywordSave("검색" + i);
		}

		int topKeywordCount = searchKeywordService.findTopKeyword().size();

		assertThat(topKeywordCount).isBetween(0, 10);
	}
}
