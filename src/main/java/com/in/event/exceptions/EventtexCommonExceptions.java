package com.in.event.exceptions;

import org.springframework.http.HttpStatus;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class EventtexCommonExceptions extends RuntimeException {

	private static final long serialVerionUID = -8985719096851045379L;

	private final HttpStatus status;

	public EventtexCommonExceptions(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

}
