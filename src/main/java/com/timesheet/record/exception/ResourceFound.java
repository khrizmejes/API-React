package com.timesheet.record.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceFound extends Exception {

	private static final long serialVersionUID = -1914660161954713718L;

	public ResourceFound(String message) {
		super(message);
	}

	public HttpStatus getStatus() {
		return HttpStatus.BAD_REQUEST;
	}
}
