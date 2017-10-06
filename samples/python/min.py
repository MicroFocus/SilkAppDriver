from selenium import webdriver

driver = webdriver.Remote(
  command_executor='http://127.0.0.1:8080',
  desired_capabilities={'app': '%WINDIR%\\notepad.exe'})

textfield = driver.find_element_by_xpath("//TextField")
textfield.clear()
textfield.send_keys("hello from python!")

driver.find_element_by_xpath("//Menu[@caption='File']").click()
driver.find_element_by_xpath("//MenuItem[@caption='Exit']").click()
driver.find_element_by_xpath("//PushButton[@caption=\"Don't Save\"]").click()

driver.quit()
