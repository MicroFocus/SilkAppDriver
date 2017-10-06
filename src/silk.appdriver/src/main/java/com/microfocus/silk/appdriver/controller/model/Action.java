package com.microfocus.silk.appdriver.controller.model;

import java.util.List;
import java.util.Map;

public class Action {
	private String id;
	private String type;

	private List<Map<String, Object>> actions;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Map<String, Object>> getActions() {
		return actions;
	}

	public void setActions(List<Map<String, Object>> actions) {
		this.actions = actions;
	}
}
