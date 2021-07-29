package com.hotel.facilitymanagementapi.exception;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@EnableWebMvc
public class DefaultExceptionHandler  extends ResponseEntityExceptionHandler{
	

	private static Logger logger=LoggerFactory.getLogger(DefaultExceptionHandler.class);
	 @ExceptionHandler(value = ResourceNotAvailableException.class)
	  public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotAvailableException ex, HttpServletRequest  request) {
	    ErrorMessage message = new ErrorMessage(
	        new Date(),404,"Not Found",
	        ex.getMessage(),request.getRequestURI());
	    return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	  }
	 
	 @ExceptionHandler(value = Exception.class)
	  public ResponseEntity<ErrorMessage> genericException(Exception ex, HttpServletRequest request) {
	    logger.error("some error occured"+ex.getMessage());
		 ErrorMessage message = new ErrorMessage(
	        new Date(),500,"Internal Error",
	        "Some Exception Occured"+ex.getMessage(),request.getRequestURI());
	    return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	  }

}
