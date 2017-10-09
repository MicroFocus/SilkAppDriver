package com.microfocus.silk.appdriver.tests;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotSame;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class NotepadDemoTest {
	private static final String APP = "%WINDIR%\\notepad.exe";

	private WebDriver driver;

	@Before
	public void startApp() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("app", APP);

		driver = new RemoteWebDriver(new URL("http://localhost:8080"), capabilities);

		driver.manage().timeouts().implicitlyWait(17, TimeUnit.SECONDS);

		String windowHandle = driver.getWindowHandle();
		assertNotNull(windowHandle);

		Point windowPosition = driver.manage().window().getPosition();
		assertNotNull(windowPosition);
	}

	@Test
	public void test() throws Exception {
		driver.manage().window().maximize();

		String title = driver.getTitle();
		assertEquals("Untitled - Notepad", title);

		WebElement textField = driver.findElement(By.xpath("//TextField")); // find
																			// element

		Rectangle rect = textField.getRect();
		assertNotNull(rect);

		textField.clear();
		assertEquals("", textField.getText());
		assertEquals("com.borland.silktest.jtf.TextField", textField.getTagName());

		WebElement window = driver.findElement(By.xpath("//Window"));
		assertEquals("com.borland.silktest.jtf.Window", window.getTagName());
		textField = window.findElement(By.xpath("//TextField")); // find element
																	// from
																	// element

		textField.sendKeys("hello");
		assertEquals("hello", textField.getText());

		textField.sendKeys(", world");
		assertEquals("hello, world", textField.getText());

		driver.manage().timeouts().implicitlyWait(42, TimeUnit.SECONDS);
	}

	@Test
	public void testGetAttribute() {
		WebElement textField = driver.findElement(By.xpath("//TextField"));
		textField.sendKeys("hello");

		// TODO: Currently (Selenium 3.6) getAttribute delegates to
		// executeJavaScript with a complex script which we can't support.
		
		String enabled = textField.getAttribute("Enabled");
		// assertEquals("true", enabled);
		// assertEquals("Consolas", textField.getAttribute("Font"));
		// assertEquals("false", textField.getAttribute("IsPassword"));
		// assertEquals("MultiLine", textField.getAttribute("true"));
	}
	
	@Test
	public void testScreenshotAPI () {
		driver.findElement(By.xpath("//TextField")).sendKeys("hello!");
		
		File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

 		assertTrue(file.length() > 0);
	}

	@Test
	public void testActionAPI() {
		WebElement textField = driver.findElement(By.xpath("//TextField"));
		textField.sendKeys("hello");
		
		Actions actions = new Actions(driver);

		actions.moveByOffset(100, 100).moveByOffset(10, 10).click().perform();
	}

	@After
	public void closeApp() {
		driver.close(); // Tries to close the window, will ask "do you want to
						// save..."

		WebElement dontSave = driver.findElement(By.xpath("//PushButton[@caption=\"Don't Save\"]"));
		dontSave.click();

		driver.quit();
	}
}
