var selenium = require('selenium-webdriver');

new selenium.Builder()
.usingServer('http://localhost:8080')
.forBrowser("firefox") // TODO: The bindings require the browserName capability to be valid (but they shouldn't and it will be ignored by the remote end anyway)
.withCapabilities({"app" : "%WINDIR%\\notepad.exe"})
.build()
  .then(driver => {
    return driver.findElement(selenium.By.xpath('//TextField')).sendKeys("Hello from JavaScript!")
      .then(_ => driver.findElement(selenium.By.xpath("//Menu[@caption='File']")).click())
      .then(_ => driver.findElement(selenium.By.xpath("//MenuItem[@caption='Exit']")).click())
      .then(_ => driver.findElement(selenium.By.xpath("//PushButton[@caption=\"Don't Save\"]")).click())
      .then(_ => driver.quit());
  });
