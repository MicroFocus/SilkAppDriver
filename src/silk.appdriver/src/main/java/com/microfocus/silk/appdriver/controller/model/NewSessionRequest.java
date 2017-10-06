package com.microfocus.silk.appdriver.controller.model;

import java.util.Map;

public class NewSessionRequest {
	private Map<String, Object> desiredCapabilities;

	public Map<String, Object> getDesiredCapabilities() {
		return desiredCapabilities;
	}

	public void setDesiredCapabilities(Map<String, Object> desiredCapabilities) {
		this.desiredCapabilities = desiredCapabilities;
	}
}
