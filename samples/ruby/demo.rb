require "rspec/expectations"
require "selenium-webdriver"
require "rspec"

# RSpec config

RSpec.configure do | config |
  config.before(:all) do
    @wait = Selenium::WebDriver::Wait.new(:timeout => 15)

    caps = Selenium::WebDriver::Remote::Capabilities.internet_explorer
    caps['app'] = "%WINDIR%\\notepad.exe"

    driver = Selenium::WebDriver.for :remote, url: "http://localhost:8080", desired_capabilities: caps

  end

  config.before(:each) do | test |
    
  end

  config.after(:each) do | test |
  end

  config.after(:all) do
    @driver.quit
  end
end

# Tests

describe "Notepad" do
  it "should be able to login with demo user" do
    puts(@driver.title)
  end
end