package com.microfocus.silk.appdriver.controller.model;

public class BaseStateInfo {
	public static enum BaseStateType {
		NATIVE, MOBILE
	}

	private BaseStateType type = BaseStateType.NATIVE;

	private String locator;
	private String executable;
	private String workingDirectory;
	private String commandLineArguments;

	private String url;
	private String connectionString;

	public BaseStateType getType() {
		return type;
	}

	public void setType(BaseStateType type) {
		this.type = type;
	}

	public String getLocator() {
		return locator;
	}

	public void setLocator(String locator) {
		this.locator = locator;
	}

	public String getExecutable() {
		return executable;
	}

	public void setExecutable(String executable) {
		this.executable = executable;
	}

	public String getWorkingDirectory() {
		return workingDirectory;
	}

	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	public String getCommandLineArguments() {
		return commandLineArguments;
	}

	public void setCommandLineArguments(String commandLineArguments) {
		this.commandLineArguments = commandLineArguments;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getConnectionString() {
		return connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

}
