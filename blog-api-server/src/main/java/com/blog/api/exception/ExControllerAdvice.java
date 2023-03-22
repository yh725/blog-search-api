package com.blog.api.exception;

import com.blog.api.dto.BaseResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler
	public BaseResponseDto jsonExHandle(RuntimeException e) {
		return new BaseResponseDto(null, e.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler
	public BaseResponseDto bindExHandle(BindException e) {
		List<ObjectError> allErrors = e.getAllErrors();
		StringBuilder str = new StringBuilder();
		int i = 1;
		for (ObjectError allError : allErrors) {
			if (i > 1) str.append(", ");
			str.append(allError.getDefaultMessage());
			i++;
		}
		return new BaseResponseDto(null, str.toString());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler
	public BaseResponseDto nullExHandle(NullPointerException e) {
		return new BaseResponseDto(null, e.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler
	public BaseResponseDto httpClientExHandle(HttpClientErrorException e) {
		return new BaseResponseDto(null, e.getMessage());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler
	public BaseResponseDto exHandle(Exception e) {
		log.info("errors ", e);
		String message = "내부 오류";
		return new BaseResponseDto(null, message);
	}
}
