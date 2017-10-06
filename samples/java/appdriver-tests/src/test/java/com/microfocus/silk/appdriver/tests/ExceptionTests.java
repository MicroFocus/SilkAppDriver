package com.microfocus.silk.appdriver.tests;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ExceptionTests {
	private static final String APP = "%WINDIR%\\notepad.exe";

	private WebDriver driver;

	@Before
	public void startApp() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("app", APP);

		driver = new RemoteWebDriver(new URL("http://localhost:8080"), capabilities);
	}

	@Test(expected = UnsupportedCommandException.class)
	public void testGoNotSupported() {
		driver.navigate().to("http://www.google.at");
	}

	@Test(expected = UnsupportedCommandException.class)
	public void testGetCurrentUrlNotSupported() {
		driver.getCurrentUrl();
	}

	@Test(expected = UnsupportedCommandException.class)
	public void testBackNotSupported() {
		driver.navigate().back();
	}

	@Test(expected = UnsupportedCommandException.class)
	public void testForwardNotSupported() {
		driver.navigate().forward();
	}

	@Test(expected = UnsupportedCommandException.class)
	public void testRefreshNotSupported() {
		driver.navigate().refresh();
	}

	@Ignore // Ignored for now - delegates to executeScript currently (Selenium
			// 3.6) which it probably shouldn't
	@Test(expected = UnsupportedCommandException.class)
	public void testgetPageSourceNotSupported() {
		driver.getPageSource();
	}

	@Test(expected = NoSuchElementException.class)
	public void testNoSuchElementException() throws Exception {
		driver.findElement(By.xpath("//Window[@caption='bla']"));
	}

	@Test(expected = UnsupportedCommandException.class)
	public void testFindByCssNotSupported() {
		driver.findElement(By.cssSelector("test"));
	}

	@Test(expected = StaleElementReferenceException.class)
	public void testStaleElementReferenceException() {
		driver.findElement(By.xpath("//Menu[@caption='Format']")).click();
		driver.findElement(By.xpath("//MenuItem[@caption='Font']")).click();

		WebElement ok = driver.findElement(By.xpath("//PushButton[@caption='OK']"));

		ok.click();

		ok.click(); // Dialog is disposed, button isn't there any more,
					// StaleElementReferenceException should be raised
	}

	@After
	public void closeApp() {
		WebElement fileMenu = driver.findElement(By.xpath("//Menu[@caption='File']"));
		fileMenu.click();

		WebElement exit = driver.findElement(By.xpath("//MenuItem[@caption='Exit']"));
		exit.click();

		driver.quit();
	}

}
