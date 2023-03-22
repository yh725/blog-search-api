package com.blog.api.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchKeywordTop {

	@Id
	private String keyword;
	private Long count;

	@Builder
	public SearchKeywordTop(String keyword, Long count) {
		this.keyword = keyword;
		this.count = count;
	}
}
