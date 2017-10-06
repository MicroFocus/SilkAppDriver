package com.microfocus.silk.appdriver.controller.model;

public class ErrorResponse extends Response {
	private String error;
	private String state;
	private String message;
	private String stacktrace;

	public ErrorResponse() {
		super(null);
	}

	public ErrorResponse(String sessionId, String error, String message) {
		super(sessionId);
		this.error = this.state = error;
		this.message = message;
	}

	public ErrorResponse(String sessionId, String error) {
		super(sessionId);
		this.message = this.error = this.state = error;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStacktrace() {
		return stacktrace;
	}

	public void setStacktrace(String stacktrace) {
		this.stacktrace = stacktrace;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
