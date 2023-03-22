package com.blog.api.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@SuperBuilder
public class BaseResponseDto {

	private Map<String, Object> data;
	private String message;

	public BaseResponseDto(Map<String, Object> data, String message) {
		this.data = data;
		this.message = message;
	}
}
