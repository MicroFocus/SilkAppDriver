package com.microfocus.silk.appdriver.controller.model;

public class GenericResponse<T> extends Response {
	private int status = 0;
	private T value;

	public GenericResponse() {
		super(null);
	}

	public GenericResponse(String sessionId, T value) {
		super(sessionId);
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String getMessage() {
		return "Status: " + status;
	}
}
