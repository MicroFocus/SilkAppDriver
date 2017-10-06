package com.microfocus.silk.appdriver.controller.model;

import java.util.ArrayList;
import java.util.List;

public class ExecuteScriptRequest {
	private String script;
	private List<Object> args = new ArrayList<>();

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public List<Object> getArgs() {
		return args;
	}

	public void setArgs(List<Object> args) {
		this.args = args;
	}

}
