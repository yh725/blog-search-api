package com.blog.api.batch.service;

import com.blog.api.entity.SearchKeyword;
import com.blog.api.entity.SearchKeywordTop;
import com.blog.api.repository.SearchKeywordJpaRepository;
import com.blog.api.repository.SearchKeywordTopJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchKeywordService {

	private final SearchKeywordJpaRepository searchKeywordJpaRepository;
	private final SearchKeywordTopJpaRepository searchKeywordTopJpaRepository;

	/**
	 * 인기 검색어 Top 10 세팅
	 */
	@Transactional
	public void findSearchKeywordTop() {

		//테이블 초기화
		searchKeywordTopJpaRepository.deleteAll();

		//검색 TOP 10 키워드 저장
		List<SearchKeyword> findKeywordTop10 = searchKeywordJpaRepository.findTop10ByOrderByCountDesc();
		for (SearchKeyword searchKeyword : findKeywordTop10) {
			SearchKeywordTop skt = SearchKeywordTop.builder()
					.keyword(searchKeyword.getKeyword())
					.count(searchKeyword.getCount())
					.build();
			searchKeywordTopJpaRepository.save(skt);
		}
	}
}
