package com.blog.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@Setter
@SuperBuilder
public class BlogSearchResponseDto extends BaseResponseDto {

//	private Map<String, Object> data;
//	private String message;

	private int currentPage;

	private int size;
	private int totalPages;

	public BlogSearchResponseDto(Map<String, Object> data, String message, int currentPage, int size, int totalPages) {
		super(data, message);
		this.currentPage = currentPage;
		this.size = size;
		this.totalPages = totalPages;
	}
}
