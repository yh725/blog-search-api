package com.blog.api.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NaverDto {

	private String lastBuildDate;
	private Integer total;
	private Integer start;
	private Integer display;
	private List<Items> items = new ArrayList<>();

	@Data
	static class Items {
		private String title;
		private String link;
		private String description;
		private String bloggername;
		private String bloggerlink;
		private String postdate;
	}
}
