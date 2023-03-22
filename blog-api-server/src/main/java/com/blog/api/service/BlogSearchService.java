package com.blog.api.service;

import com.blog.api.dto.BlogSearchRequestDto;

public interface BlogSearchService {

	Object BlogSearch(BlogSearchRequestDto blogSearchDto) throws Exception;
}
