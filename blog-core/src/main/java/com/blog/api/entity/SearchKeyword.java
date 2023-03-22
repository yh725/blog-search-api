package com.blog.api.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchKeyword {

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String keyword;
	private Long count;

	public SearchKeyword(String keyword, Long count) {
		this.keyword = keyword;
		this.count = count;
	}

	public void KeywordCountAdd() {
		this.count = this.count + 1;
	}
}
