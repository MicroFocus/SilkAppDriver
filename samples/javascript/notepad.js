var selenium = require('selenium-webdriver');
var reporters = require('jasmine-reporters');

var until = selenium.until;

var junitReporter = new reporters.JUnitXmlReporter({
  savePath: __dirname,
  consolidateAll: true
});
jasmine.getEnv().addReporter(junitReporter);

jasmine.DEFAULT_TIMEOUT_INTERVAL = 30000

describe('InsuranceWeb Tests', function () {
  beforeAll(function (done) {

    jasmine.DEFAULT_TIMEOUT_INTERVAL = 30000

    new selenium.Builder()
      .usingServer('http://localhost:8080')
      .withCapabilities({"app" : "%WINDIR%\\notepad.exe"})
      .forBrowser("firefox")
      .build()
      .then(function (innerDriver) {
        driver = innerDriver;

        driver.executeScript("appdriver:startTrueLog", process.cwd() + "\\result.tlz").then(done);
      });
  });

  beforeEach(function (done) {
    done();
  });

  afterEach(function (done) {
    done();
  });

  it('should type something', function (done) {
    driver.findElement(selenium.By.xpath('//TextField')).sendKeys("Hello from JavaScript!")
    .then(_ => driver.findElement(selenium.By.xpath("//Menu[@caption='File']")).click())
    .then(_ => driver.findElement(selenium.By.xpath("//MenuItem[@caption='Exit']")).click())
    .then(_ => driver.findElement(selenium.By.xpath("//PushButton[@caption=\"Don't Save\"]")).click())

    .then(done);
  });

  afterAll(function (done) {
    driver.executeScript("appdriver:stopTrueLog")
    .then(_ => driver.quit())
    .then(done);
  });
});
