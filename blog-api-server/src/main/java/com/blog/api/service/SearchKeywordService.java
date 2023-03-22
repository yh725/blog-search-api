package com.blog.api.service;

import com.blog.api.dto.SearchKeywordTopDto;
import com.blog.api.entity.SearchKeyword;
import com.blog.api.repository.SearchKeywordJpaRepository;
import com.blog.api.repository.SearchKeywordTopJpaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchKeywordService {

	private final EntityManager em;
	private final ModelMapper modelMapper;
	private final SearchKeywordJpaRepository searchKeywordJpaRepository;
	private final SearchKeywordTopJpaRepository searchKeywordTopJpaRepository;

	/**
	 * 검색 키워드 저장
	 */
	@Transactional
	public void searchKeywordSave(String keyword) {
		// 검색 후 카운트 +1;
		SearchKeyword findKeyword = searchKeywordJpaRepository.findByKeyword(keyword);

		if (findKeyword == null) {
			SearchKeyword sk = new SearchKeyword(keyword, 1L);
			searchKeywordJpaRepository.save(sk);
			em.flush();
		} else {
			findKeyword.KeywordCountAdd();
		}
	}

	/**
	 * 인기 검색어 전체 조회
	 */
	public List<SearchKeywordTopDto> findTopKeyword() {
		return searchKeywordTopJpaRepository.findAll()
				.stream()
				.map(entity -> modelMapper.map(entity, SearchKeywordTopDto.class))
				.collect(Collectors.toList());
	}

	/**
	 * 인기 검색어 일부 조회
	 */
	public List<SearchKeywordTopDto> findTopKeywordLimit(int size) {
		return searchKeywordTopJpaRepository.findTopKeyword(size)
				.stream()
				.map(entity -> modelMapper.map(entity, SearchKeywordTopDto.class))
				.collect(Collectors.toList());
	}

	/**
	 * 키워드 검색 횟수
	 */
	public Long findSearchKeywordCount(String keyword) {
		return searchKeywordJpaRepository.findByKeyword(keyword).getCount();
	}
}
