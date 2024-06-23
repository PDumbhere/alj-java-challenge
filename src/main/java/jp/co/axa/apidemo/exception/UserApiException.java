package jp.co.axa.apidemo.exception;

import org.springframework.http.HttpStatus;


public class UserApiException extends RuntimeException {

	private HttpStatus httpStatus;
	private String message;
	
	public UserApiException(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
