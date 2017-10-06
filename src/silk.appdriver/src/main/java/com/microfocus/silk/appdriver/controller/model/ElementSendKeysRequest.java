package com.microfocus.silk.appdriver.controller.model;

import java.util.List;

public class ElementSendKeysRequest {
	private String id;
	private List<String> value;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getValue() {
		return value;
	}

	public void setValue(List<String> value) {
		this.value = value;
	}
}
