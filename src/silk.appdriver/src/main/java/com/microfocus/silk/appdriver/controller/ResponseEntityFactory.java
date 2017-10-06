package com.microfocus.silk.appdriver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.microfocus.silk.appdriver.controller.model.ErrorResponse;
import com.microfocus.silk.appdriver.controller.model.GenericResponse;
import com.microfocus.silk.appdriver.controller.model.ProtocolError;
import com.microfocus.silk.appdriver.controller.model.Response;

public class ResponseEntityFactory {
	public <T> ResponseEntity<Response> success(String sessionId, T value) {
		return ResponseEntity.<Response>ok(new GenericResponse<T>(sessionId, value));
	}

	public ResponseEntity<Response> success(String sessionId) {
		return ResponseEntity.<Response>ok(new GenericResponse<String>(sessionId, ""));
	}

	public ResponseEntity<Response> error(String sessionId, ProtocolError error) {
		return new ResponseEntity<Response>(new ErrorResponse(sessionId, error.getMessage()),
				HttpStatus.valueOf(error.getHttpStatus()));
	}

}
