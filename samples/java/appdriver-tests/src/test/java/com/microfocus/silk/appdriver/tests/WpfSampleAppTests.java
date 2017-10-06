package com.microfocus.silk.appdriver.tests;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WpfSampleAppTests {
	private static final String APP = "C:\\tools\\sampleapplications\\dotnet\\4.0\\Wpf Sample Application\\SilkTest.Wpf.TestApplication.exe";

	private WebDriver driver;

	@Before
	public void startApp() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		Map<String, Object> baseState = new HashMap<>();
		baseState.put("type", "NATIVE");
		baseState.put("executable", APP);
		baseState.put("locator", "//WPFWindow");

		capabilities.setCapability("appdriver-basestate", baseState);

		Map<String, Object> options = new HashMap<>();
		options.put("closeOnQuit", true);

		capabilities.setCapability("appdriver-options", options);

		driver = new RemoteWebDriver(new URL("http://localhost:8080"), capabilities);
	}

	@Test
	public void test() {
		driver.findElement(By.xpath("//WPFMenuItem[@caption='File']")).click();
		driver.findElement(By.xpath("//WPFMenuItem[@caption='Open']")).click();
		driver.findElement(By.xpath("//WPFMenuItem[@caption='Standard Window']")).click();

		WebElement modalWindow = driver.findElement(By.xpath("/WPFWindow[@caption='Standard Window']"));
		assertEquals("com.microfocus.silktest.jtf.wpf.WPFWindow", modalWindow.getTagName());

		modalWindow.findElement(By.xpath("//WPFButton[@caption='OK']")).click();

		driver.findElement(By.xpath("//WPFMenuItem[@caption='Controls']")).click();
		driver.findElement(By.xpath("//WPFMenuItem[@caption='Basic Controls']")).click();

		WebElement basicControlsWindow = driver.findElement(By.xpath("/WPFWindow[@caption='Basic Controls']"));
		assertEquals("com.microfocus.silktest.jtf.wpf.WPFWindow", basicControlsWindow.getTagName());

		basicControlsWindow
				.findElement(By.xpath("//WPFTabControl[@automationId='tabControl']/WPFTabItem[@caption='Text']"))
				.click();
		basicControlsWindow.findElement(By.xpath("//WPFTextBox[@automationId='textBoxSingle']"))
				.sendKeys("this is some text");
	}

	@Test
	public void testFindElements() {
		driver.findElement(By.xpath("//WPFMenuItem[@caption='Controls']")).click();
		driver.findElement(By.xpath("//WPFMenuItem[@caption='Basic Controls']")).click();

		WebElement basicControlsWindow = driver.findElement(By.xpath("/WPFWindow[@caption='Basic Controls']"));
		basicControlsWindow
				.findElement(By.xpath("//WPFTabControl[@automationId='tabControl']/WPFTabItem[@caption='Button']"))
				.click();

		// find elements
		List<WebElement> buttons = driver.findElements(By.xpath("//WPFButton"));

		assertEquals(6, buttons.size());

		WebElement buttonA = buttons.get(0);

		assertEquals("Button _A", buttonA.getText());
		assertEquals("com.microfocus.silktest.jtf.wpf.WPFButton", buttonA.getTagName());

		WebElement textField = driver.findElement(By.xpath("//WPFTextBox"));
		assertEquals("", textField.getText());
		buttonA.click();
		assertEquals("Button A pressed", textField.getText());

		// find elements from element
		List<WebElement> textBoxes = basicControlsWindow.findElements(By.xpath("//WPFTextBox"));
		assertEquals(2, textBoxes.size());

		// find non existent elements
		List<WebElement> shouldBeEmpty = driver.findElements(By.xpath("//SAPButton"));

		assertEquals(0, shouldBeEmpty.size());
	}

	@After
	public void closeApp() {
		if (driver != null) {
			driver.quit();
		}
	}
}
