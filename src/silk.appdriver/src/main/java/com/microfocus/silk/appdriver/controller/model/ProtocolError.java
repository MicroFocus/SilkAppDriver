package com.microfocus.silk.appdriver.controller.model;

public enum ProtocolError {
	ELEMENT_CLICK_INTERCEPTED("element click intercepted", 400, "The Element Click command could not be completed because the element receiving the events is obscuring the element that was requested clicked."),
	ELEMENT_NOT_SELECTABLE("element not selectable", 400, "An attempt was made to select an element that cannot be selected."),
	ELEMENT_NOT_INTERACTABLE("element not interactable", 400, "A command could not be completed because the element is not pointer- or keyboard interactable."),
	INSECURE_CERTIFICATE("insecure certificate", 400, "Navigation caused the user agent to hit a certificate warning, which is usually the result of an expired or invalid TLS certificate."),
	INVALID_ARGUMENT("invalid argument", 400, "The arguments passed to a command are either invalid or malformed."),
	INVALID_COOKIE_DOMAIN("invalid cookie domain", 400, "An illegal attempt was made to set a cookie under a different domain than the current page."),
	INVALID_COORDINATES("invalid coordinates", 400, "The coordinates provided to an interactions operation are invalid."),
	INVALID_ELEMENT_STATE("invalid element state", 400, "A command could not be completed because the element is in an invalid state, e.g. attempting to click an element that is no longer attached to the document."),
	INVALID_SELECTOR("invalid selector", 400, "Argument was an invalid selector."),
	INVALID_SESSION_ID("invalid session id", 404, "Occurs if the given session id is not in the list of active sessions, meaning the session either does not exist or that it�s not active."),
	JAVASCRIPT_ERROR("javascript error", 500, "An error occurred while executing JavaScript supplied by the user."),
	MOVE_TARGET_OUT_OF_BOUNDS("move target out of bounds", 500, "The target for mouse interaction is not in the browser�s viewport and cannot be brought into that viewport."),
	NO_SUCH_ALERT("no such alert", 400, "An attempt was made to operate on a modal dialog when one was not open."),
	NO_SUCH_COOKIE("no such cookie", 404, "No cookie matching the given path name was found amongst the associated cookies of the current browsing context�s active document."),
	NO_SUCH_ELEMENT("no such element", 404, "An element could not be located on the page using the given search parameters."),
	NO_SUCH_FRAME("no such frame", 400, "A command to switch to a frame could not be satisfied because the frame could not be found."),
	NO_SUCH_WINDOW("no such window", 400, "A command to switch to a window could not be satisfied because the window could not be found."),
	SCRIPT_TIMEOUT("script timeout", 408, "A script did not complete before its timeout expired."),
	SESSION_NOT_CREATED("session not created", 500, "A new session could not be created."),
	STALE_ELEMENT_REFERENCE("stale element reference", 400, "A command failed because the referenced element is no longer attached to the DOM."),
	TIMEOUT("timeout", 408, "An operation did not complete before its timeout expired."),
	UNABLE_TO_SET_COOKIE("unable to set cookie", 500, "A command to set a cookie�s value could not be satisfied."),
	UNABLE_TO_CAPTURE_SCREEN("unable to capture screen", 500, "A screen capture was made impossible."),
	UNEXPECTED_ALERT_OPEN("unexpected alert open", 500, "A modal dialog was open, blocking this operation."),
	UNKNOWN_COMMAND("unknown command", 404, "A command could not be executed because the remote end is not aware of it."),
	UNKNOWN_ERROR("unknown error", 500, "An unknown error occurred in the remote end while processing the command."),
	UNKNOWN_METHOD("unknown method", 405, "The requested command matched a known URL but did not match an method for that URL."),
	UNSUPPORTED_OPERATION("unsupported operation", 500, "Indicates that a command that should have executed properly cannot be supported for some reason.");
		
	private String message;
	private int httpStatus;
	private String description;

	ProtocolError(String message, int httpStatus, String description) {
		this.message = message;
		this.httpStatus = httpStatus;
		this.description = description;
	}

	public String getMessage() {
		return message;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public String getDescription() {
		return description;
	}

}
