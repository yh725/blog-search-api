package com.blog.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BlogSearchRequestDto {

	@NotNull(message = "query는 필수 파라미터 입니다.")
	@NotEmpty(message = "query는 비어 있을 수 없습니다.")
	@Schema(description = "검색어 (필수값)", defaultValue = "검색어")
	private String query;

	@Schema(description = "정렬 방식", defaultValue = "accuracy", allowableValues = {"accuracy:정확도순", "recency: 최신순"})
	private String sort = "accuracy";   //accuracy:정확도순, recency: 최신순

	@Schema(description = "결과 페이지 (1 ~ 50)", defaultValue = "1")
	@Range(min = 1, max = 50, message = "page는 1에서 50 사이여야 합니다.")
	private Integer page = 1;

	@Schema(description = "한 페이지 문서 수 (1 ~ 50)", defaultValue = "10")
	@Range(min = 1, max = 50, message = "size는 1에서 50 사이여야 합니다.")
	private Integer size = 10;
}
