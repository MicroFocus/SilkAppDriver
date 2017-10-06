package com.microfocus.silk.appdriver.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MobileTests {
	@Ignore
	@Test
	public void test() throws IOException {
		Map<String, String> baseState = new HashMap<>();
		baseState.put("type", "MOBILE");
		baseState.put("connectionString", "platformName=Android;app=C:\\apps\\InsuranceMobile.apk");

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("appdriver-basestate", baseState);

		WebDriver driver = new RemoteWebDriver(new URL("http://localhost:8080"), capabilities);
		String title = driver.getTitle();

		assertEquals("", title); // TODO: Maybe we find something to use as the
									// title?

		WebElement email = driver
				.findElement(By.xpath("//MobileTextField[@resource-id='silktest.insurancemobile:id/email']"));
		email.sendKeys("test");
		assertEquals("test", email.getText());
		
		email.clear();
		assertEquals("", email.getText());
		email.sendKeys("john.smith@gmail.com");

		driver.findElement(By.xpath("//MobileTextField[@resource-id='silktest.insurancemobile:id/password']"))
				.sendKeys("john");

		driver.findElement(By.xpath("//MobileButton[@resource-id='silktest.insurancemobile:id/log_in_button']"))
				.click();

		driver.findElement(By.xpath("//MobileObject[@content-desc='drawer_open']")).click();

		driver.findElement(By.xpath("//MobileObject[@caption='Logout']")).click();

		driver.quit();
	}
}
