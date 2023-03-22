package com.blog.api.batch.scheduler;

import com.blog.api.batch.service.SearchKeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchKeywordTop10Scheduler {

	private final SearchKeywordService searchKeywordService;

	@Scheduled(fixedRate = 1000 * 60) //단위 : 초
	public void KeywordTop10() {
		searchKeywordService.findSearchKeywordTop();
	}
}
