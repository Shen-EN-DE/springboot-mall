package com.example.demo.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> handleHttpMessageReadable(HttpMessageNotReadableException ex){
		//這一行代碼獲取到了異常的根本原因，這對於準確地確定問題所在是很重要的。
		Throwable rootCause = ex.getRootCause();
		if(rootCause instanceof InvalidFormatException) {
			InvalidFormatException ife = (InvalidFormatException) rootCause;
			Map<String, Object> body = new LinkedHashMap<>() ;
			body.put("timestamp", new Date());
			body.put("status", HttpStatus.BAD_REQUEST.value());
			body.put("error", "bad request");
			body.put("message", "Invalid input: " + ife.getValue() + " is not valid for field " 
							+ ife.getPath().get(0).getFieldName());
			
			return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
				"message", "Error parsing Json request",
				"error", ex.getMessage()
		));
	}
}
