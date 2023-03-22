package com.blog.api.repository;

import com.blog.api.entity.SearchKeywordTop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchKeywordTopJpaRepository extends JpaRepository<SearchKeywordTop, String> {

	@Query(value = "SELECT * FROM Search_Keyword_Top ORDER BY count DESC limit :size", nativeQuery = true)
	List<SearchKeywordTop> findTopKeyword(@Param("size") int size);
}
