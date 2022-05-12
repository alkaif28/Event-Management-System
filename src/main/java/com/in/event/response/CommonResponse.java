package com.in.event.response;

import lombok.Data;

@Data
public class CommonResponse {

	private int statusCode;
	private String message;
	private Object data;
}
