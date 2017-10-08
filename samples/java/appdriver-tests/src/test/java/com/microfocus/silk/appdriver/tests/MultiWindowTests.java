package com.microfocus.silk.appdriver.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MultiWindowTests {
	private static final String APP = "%WINDIR%\\notepad.exe";

	private WebDriver driver;

	@Before
	public void startApp() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		Map<String, Object> baseState = new HashMap<>();
		baseState.put("type", "NATIVE");
		baseState.put("executable", APP);
		baseState.put("locator", "//Window");

		capabilities.setCapability("appdriver-basestate", baseState);

		Map<String, Object> options = new HashMap<>();
		options.put("closeOnQuit", true);

		capabilities.setCapability("appdriver-options", options);

		driver = new RemoteWebDriver(new URL("http://localhost:8080"), capabilities);
	}

	@Test
	public void test() {
		assertEquals(1, driver.getWindowHandles().size());

		String mainWindowHandle = driver.getWindowHandle();
		assertNotNull(mainWindowHandle);

		Point windowPosition = driver.manage().window().getPosition();
		assertNotNull(windowPosition);

		driver.findElement(By.xpath("//Menu[@caption='Help']")).click();
		driver.findElement(By.xpath("//MenuItem[@caption='About Notepad']")).click();

		assertEquals(2, driver.getWindowHandles().size());

		assertEquals("About Notepad", driver.getTitle());

		String aboutDialogHandle = driver.getWindowHandle();

		Assert.assertNotSame(mainWindowHandle, aboutDialogHandle);

		driver.findElement(By.xpath("//PushButton[@caption='OK']")).click();
		assertEquals(1, driver.getWindowHandles().size());

		assertEquals("Untitled - Notepad", driver.getTitle());

		driver.findElement(By.xpath("//TextField")).sendKeys("text");

		driver.findElement(By.xpath("//Menu[@caption='File']")).click();
		driver.findElement(By.xpath("//MenuItem[@caption='Exit']")).click();

		// Do you want to save?

		assertEquals(2, driver.getWindowHandles().size());
		assertEquals("Notepad", driver.getTitle());

		driver.close(); // Close current window

		assertEquals(1, driver.getWindowHandles().size());
		assertEquals("Untitled - Notepad", driver.getTitle());
	}

	@Test
	public void testSwitchToWindow() {
		assertEquals(1, driver.getWindowHandles().size());

		String mainWindowHandle = driver.getWindowHandle();

		driver.findElement(By.xpath("//Menu[@caption='Edit']")).click();
		driver.findElement(By.xpath("//MenuItem[@caption='Replace']")).click();

		assertEquals(2, driver.getWindowHandles().size());

		String replaceWindowHandle = driver.getWindowHandle();

		assertEquals("Replace", driver.getTitle());
		driver.switchTo().window(mainWindowHandle);
		assertEquals("Untitled - Notepad", driver.getTitle());

		driver.switchTo().window(replaceWindowHandle);
		assertEquals("Replace", driver.getTitle());
	}
	
	@Test(expected = NoSuchWindowException.class)
	public void testSwitchToInvalidWindow() {
		assertEquals(1, driver.getWindowHandles().size());

		driver.switchTo().window("42");
	}

	@After
	public void closeApp() {
		driver.findElement(By.xpath("//TextField")).clear();
		driver.quit();
	}

}
