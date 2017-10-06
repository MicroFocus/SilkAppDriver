package com.microfocus.silk.appdriver.controller.model;

import java.util.Map;

public class NewSessionResult {
	private String sessionId;
	private Map<String, Object> capabilities;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Map<String, Object> getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(Map<String, Object> capabilities) {
		this.capabilities = capabilities;
	}
}
