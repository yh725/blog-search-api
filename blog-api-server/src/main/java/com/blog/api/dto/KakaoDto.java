package com.blog.api.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class KakaoDto {

	public Meta meta;

	public List<Documents> documents = new ArrayList<>();

	@Data
	public static class Meta {
		private Integer total_count;
		private Integer pageable_count;
		private Boolean is_end;
	}

	@Data
	static class Documents {
		private String title;
		private String contents;
		private String url;
		private String blogname;
		private String thumbnail;
		private String datetime;
	}

	public Integer totalCount() {
		return this.meta.getPageable_count();
	}
}
