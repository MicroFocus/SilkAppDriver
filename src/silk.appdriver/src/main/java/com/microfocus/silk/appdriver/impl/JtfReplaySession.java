package com.microfocus.silk.appdriver.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import com.borland.silktest.jtf.BaseState;
import com.borland.silktest.jtf.Desktop;
import com.borland.silktest.jtf.IClickable;
import com.borland.silktest.jtf.IKeyable;
import com.borland.silktest.jtf.IMoveable;
import com.borland.silktest.jtf.MobileBaseState;
import com.borland.silktest.jtf.TestObject;
import com.borland.silktest.jtf.TextField;
import com.borland.silktest.jtf.common.CommonOptions;
import com.borland.silktest.jtf.common.ObjectNotFoundException;
import com.borland.silktest.jtf.common.TruelogScreenshotMode;
import com.borland.silktest.jtf.common.types.FindOptions;
import com.borland.silktest.jtf.common.types.Point;
import com.borland.silktest.jtf.common.types.Rect;
import com.borland.silktest.jtf.internal.TrueLogProxy;
import com.google.common.base.Preconditions;
import com.microfocus.silk.appdriver.controller.model.AppDriverOptions;
import com.microfocus.silk.appdriver.controller.model.BaseStateInfo;
import com.microfocus.silktest.jtf.mobile.IMobileClickable;
import com.microfocus.silktest.jtf.mobile.IMobileKeyable;
import com.microfocus.silktest.jtf.mobile.MobileTextField;

public class JtfReplaySession implements IReplaySession {
	private String id = UUID.randomUUID().toString();

	private Desktop desktop = new Desktop();

	private TestObject rootWindow;

	private Map<String, TestObject> elements = new HashMap<>();

	private AppDriverOptions options;

	public JtfReplaySession(BaseStateInfo baseStateInfo, AppDriverOptions options) {
		this.options = options == null ? new AppDriverOptions() : options;

		switch (baseStateInfo.getType()) {
		case NATIVE:
			Preconditions.checkNotNull(baseStateInfo.getExecutable());
			// TODO: Match other baseState properties here as well

			rootWindow = new BaseState(baseStateInfo.getExecutable(),
					baseStateInfo.getLocator() == null ? "//Window" : baseStateInfo.getLocator()).execute(desktop);
			break;

		case MOBILE:
			Preconditions.checkNotNull(baseStateInfo.getConnectionString());

			rootWindow = new MobileBaseState(baseStateInfo.getConnectionString()).execute(desktop);

			break;
		}
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String findElementByXpath(String xpath, String parentElementId) {
		TestObject parentObject = desktop;

		if (parentElementId != null) {
			parentObject = elements.get(parentElementId);
		}

		TestObject object = parentObject.find(xpath, new FindOptions(false));

		if (object == null) { // NoSuchElement
			return null;
		}

		String result = UUID.randomUUID().toString();
		elements.put(result, object);

		return result;
	}

	@Override
	public List<String> findAllElementsByXpath(String xpath, String parentElementId) {
		TestObject parentObject = desktop;

		if (parentElementId != null) {
			parentObject = elements.get(parentElementId);
		}

		List<String> result = new ArrayList<>();

		List<TestObject> objects = parentObject.findAll(xpath, new FindOptions(false));

		for (TestObject object : objects) {
			String id = UUID.randomUUID().toString();
			elements.put(id, object);

			result.add(id);
		}

		return result;
	}

	@Override
	public String getCurrentWindowTitle() {
		return getCurrentWindow().getText();
	}

	@Override
	public int getCurrentWindowHandle() {
		return getCurrentWindow().getHandle().getHandle();
	}

	@Override
	public List<String> getWindowHandles() {
		List<TestObject> allWindows = getAllWindows();

		List<String> handles = allWindows.stream().map(w -> String.valueOf(w.getHandle().getHandle()))
				.collect(Collectors.toList());

		return handles;
	}

	@Override
	public Rect getCurrentWindowRect() {
		return getCurrentWindow().getRect();
	}

	@Override
	public void setCurrentWindowRect(int x, int y, int width, int height) {
		TestObject window = getCurrentWindow();

		if (window instanceof IMoveable) {
			((IMoveable) window).size(width, height);
			((IMoveable) window).move(new Point(x, y));
		} else {
			// TODO: Throw
		}

	}

	@Override
	public void closeCurrentWindow() {
		TestObject currentWindow = getCurrentWindow();
		if (currentWindow instanceof IMoveable) {
			((IMoveable) currentWindow).close();
		} else {
			// TODO: Throw
		}
	}

	@Override
	public void maximizeCurrentWindow() {
		TestObject window = getCurrentWindow();

		if (window instanceof IMoveable) {
			((IMoveable) window).maximize();
		} else {
			// TODO: Throw
		}
	}

	@Override
	public void minimizeCurrentWindow() {
		TestObject window = getCurrentWindow();

		if (window instanceof IMoveable) {
			((IMoveable) window).minimize();
		} else {
			// TODO: Throw
		}
	}

	@Override
	public void switchToWindow(String windowHandle) {
		int handle = Integer.parseInt(windowHandle);

		Optional<TestObject> window = getAllWindows().stream().filter(w -> w.getHandle().getHandle() == handle)
				.findFirst();

		if (window.isPresent()) {
			if (window.get() instanceof IMoveable) {
				((IMoveable) window.get()).setActive();
			} else {
				// TODO: Throw
			}
		} else {
			throw new ObjectNotFoundException(windowHandle);
		}
	}

	@Override
	public void clickElement(String elementId) {
		TestObject object = elements.get(elementId);

		if (object instanceof IClickable) {
			((IClickable) object).click();
		} else if (object instanceof IMobileClickable) {
			((IMobileClickable) object).click();
		} else {
			// TODO: Exception
		}
	}

	@Override
	public void sendKeysToElement(String elementId, String keys) {
		TestObject object = elements.get(elementId);

		if (object instanceof IKeyable) {
			((IKeyable) object).typeKeys(keys);
		} else if (object instanceof IMobileKeyable) {
			((IMobileKeyable) object).typeKeys(keys);
		} else {
			// TODO: Exception
		}
	}

	@Override
	public void clearElement(String elementId) {
		TestObject object = elements.get(elementId);

		if (object instanceof TextField) {
			((TextField) object).clearText();
		} else if (object instanceof MobileTextField) {
			((MobileTextField) object).clearText();
		} else {
			// TODO: Exception
		}
	}

	@Override
	public String getElementText(String elementId) {
		TestObject object = elements.get(elementId);

		return object.getText();
	}

	@Override
	public String getElementClassName(String elementId) {
		TestObject object = elements.get(elementId);

		return object.getHandle().getClassName();
	}

	@Override
	public Rect getElementRect(String elementId) {
		TestObject object = elements.get(elementId);

		return object.getRect();
	}

	@Override
	public Object getElementProperty(String elementId, String name) {
		TestObject object = elements.get(elementId);

		Object result = object.getProperty(name);

		return result;
	}

	@Override
	public boolean isElementEnabled(String elementId) {
		TestObject object = elements.get(elementId);

		Optional<String> enabledMethod = object.getDynamicMethodList().stream().filter(m -> m.equals("Enabled"))
				.findFirst();
		if (enabledMethod.isPresent()) {
			return (boolean) object.getProperty("Enabled");
		} else {
			try {
				Method method = object.getClass().getMethod("isEnabled");

				boolean enabled = (boolean) method.invoke(object);

				return enabled;
			} catch (Exception e) {
				throw new RuntimeException(e); // TODO: Better error handling
			}
		}
	}

	@Override
	public Object executeScript(String script, List<Object> args) {
		if ("appdriver:startTrueLog".equals(script)) {
			String location = args.get(0).toString(); // TODO

			desktop.setOption(CommonOptions.OPT_TRUELOG_SCREENSHOT_MODE, TruelogScreenshotMode.ActiveApplication); // TODO:
																													// Add
																													// better
																													// option
																													// handling
																													// to
																													// turn
																													// on/off
																													// screenshots

			TrueLogProxy.getInstance().startTrueLog(location);
			TrueLogProxy.getInstance().testCaseStart("Demo", "demo");
		} else if ("appdriver:stopTrueLog".equals(script)) {
			TrueLogProxy.getInstance().testCaseEnd();
			TrueLogProxy.getInstance().stopTrueLog();
		}

		return true;
	}

	@Override
	public void delete() {
		if (options.isCloseOnQuit()) {
			((IMoveable) rootWindow).closeSynchron();
		}

		desktop.detachAll();
	}

	private TestObject getCurrentWindow() {
		List<TestObject> allWindows = getAllWindows();

		Optional<TestObject> activeWindow = allWindows.stream()
				.filter(w -> w instanceof IMoveable && ((IMoveable) w).isActive()).findFirst();

		if (!activeWindow.isPresent()) {
			return rootWindow;
		} else {
			return activeWindow.get();
		}
	}

	@Override
	public String takeScreenshot() {
		File tempFile;
		try {
			tempFile = File.createTempFile("SilkAppDriver", ".png");
			desktop.captureBitmap(tempFile.getAbsolutePath());

			byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(tempFile));
			
			Files.delete(tempFile.toPath());
			
			return new String(encoded, StandardCharsets.US_ASCII);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private List<TestObject> getAllWindows() {
		return desktop.findAll("//Window", new FindOptions(100));
	}
}
