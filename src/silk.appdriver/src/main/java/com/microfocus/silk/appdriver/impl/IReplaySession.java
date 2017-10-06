package com.microfocus.silk.appdriver.impl;

import java.util.List;

import com.borland.silktest.jtf.common.types.Rect;

public interface IReplaySession {

	String getId();

	String findElementByXpath(String xpath, String parentElementId);

	List<String> findAllElementsByXpath(String xpath, String parentElementId);

	String getCurrentWindowTitle();

	int getCurrentWindowHandle();

	List<Integer> getWindowHandles();

	Rect getCurrentWindowRect();
	
	void setCurrentWindowRect(int x, int y, int width, int height);

	void closeCurrentWindow();

	void maximizeCurrentWindow();

	void minimizeCurrentWindow();

	void clickElement(String elementId);

	void sendKeysToElement(String elementId, String keys);

	void clearElement(String elementId);

	String getElementText(String elementId);

	String getElementClassName(String elementId);

	Rect getElementRect(String elementId);

	Object getElementProperty(String elementId, String name);
	
	Object executeScript(String script, List<Object> args);
	
	void delete();
}