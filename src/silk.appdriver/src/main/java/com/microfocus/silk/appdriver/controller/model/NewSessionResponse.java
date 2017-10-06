package com.microfocus.silk.appdriver.controller.model;

public class NewSessionResponse extends Response {
	NewSessionResult newSession;

	public NewSessionResponse(NewSessionResult newSession) {
		super(newSession.getSessionId());

		this.newSession = newSession;
	}

	public NewSessionResult getValue() {
		return newSession;
	}

	@Override
	public String getMessage() {
		return "SessionID: " + newSession.getSessionId();
	}
}
