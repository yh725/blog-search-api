package com.blog.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class TopKeywordRequestDto {

	@Range(min = 1, max = 10, message = "size는 1에서 10 사이여야 합니다.")
	@Schema(description = "인기 검색어 검색 개수 1 ~ 10", defaultValue = "10")
	private Integer size = 10;
}
