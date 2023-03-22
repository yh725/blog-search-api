package com.blog.api.repository;

import com.blog.api.entity.SearchKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.List;

public interface SearchKeywordJpaRepository extends JpaRepository<SearchKeyword, Long> {

	//키워드 검색
	@Lock(LockModeType.PESSIMISTIC_READ) //다른 트랜잭션에서 읽기는 가능하지만 쓰기는 불가능
	SearchKeyword findByKeyword(String keyword);

	//Top 10 검색
	List<SearchKeyword> findTop10ByOrderByCountDesc();
}
