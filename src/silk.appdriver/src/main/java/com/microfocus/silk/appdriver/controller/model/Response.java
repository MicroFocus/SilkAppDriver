package com.microfocus.silk.appdriver.controller.model;

public abstract class Response {
	private String sessionId;

	public Response(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public abstract String getMessage();
}
