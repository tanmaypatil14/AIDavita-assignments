package com.citiustech.exception;

import java.time.LocalDateTime;

public class ErrorMessage {

	private String responseMessage;
	private String statusMessage;
	private Integer statusCode;
	private LocalDateTime timestamp;

	public ErrorMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErrorMessage(String responseMessage, String statusMessage, Integer statusCode, LocalDateTime timestamp) {
		super();
		this.responseMessage = responseMessage;
		this.statusMessage = statusMessage;
		this.statusCode = statusCode;
		this.timestamp = timestamp;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "ErrorMessage [responseMessage=" + responseMessage + ", statusMessage=" + statusMessage + ", statusCode="
				+ statusCode + ", timestamp=" + timestamp + "]";
	}

}
