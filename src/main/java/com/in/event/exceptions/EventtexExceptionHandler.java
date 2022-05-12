package com.in.event.exceptions;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;

import com.in.event.response.CommonResponse;

@ControllerAdvice
public class EventtexExceptionHandler {

	 public ResponseEntity<CommonResponse> eventNotFound(EventtexCommonExceptions exceptions){
		  CommonResponse commonResponse = new CommonResponse();
		 
		return null;
		  
		  
	 }
}
