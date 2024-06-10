package com.citiustech.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private ErrorMessage errorMessage = new ErrorMessage();
	
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception e, WebRequest request) {

		errorMessage.setResponseMessage(e.getLocalizedMessage());
		errorMessage.setStatusMessage(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		errorMessage.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorMessage.setTimestamp(LocalDateTime.now());

		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = { TreatmentDetailsNotFoundException.class })
	public ResponseEntity<ErrorMessage> handleSpecificException(TreatmentDetailsNotFoundException e, WebRequest request) {

		errorMessage.setResponseMessage(e.getLocalizedMessage());
		errorMessage.setStatusMessage(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		errorMessage.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorMessage.setTimestamp(LocalDateTime.now());

		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
