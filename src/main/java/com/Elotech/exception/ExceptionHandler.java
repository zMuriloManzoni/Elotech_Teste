package com.Elotech.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {
	@org.springframework.web.bind.annotation.ExceptionHandler(DebitoException.class)
	public final ExceptionResponse debitoExceptionHandler(Exception ex) {
		return new ExceptionResponse(ex.getMessage());		
	}

}
