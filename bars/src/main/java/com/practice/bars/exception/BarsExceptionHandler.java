package com.practice.bars.exception;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BarsExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity <BarsExceptionResponse> handleException (HttpServletRequest httpServletRequest, BarsException barsException) {
		
		BarsExceptionResponse error = new BarsExceptionResponse();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(barsException.getMessage());
		error.setTimeStamp(ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		
		error.setError(HttpStatus.BAD_REQUEST.BAD_REQUEST.name());
		error.setPath(httpServletRequest.getRequestURI());
		
		return new ResponseEntity<BarsExceptionResponse>(error, HttpStatus.BAD_REQUEST);
		
	}
	
	
	
}
