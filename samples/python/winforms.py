import unittest
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.select import Select
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

class InsuranceTests(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.driver = driver = webdriver.Remote(
            command_executor='http://127.0.0.1:8080',
            desired_capabilities={
                'appdriver-basestate': { 'executable' : 'C:\\tools\\sampleapplications\\dotnet\\4.0\\WindowsForms Sample Application\\SilkTest.WinForms.TestApp.exe', 'locator' : '//Window' },
                'appdriver-options': { 'closeOnQuit' : True }
              }
            )

        cls.wait = WebDriverWait(cls.driver, 10)

    def setUp(self):        
        self.driver.execute_script("appdriver:startTrueLog", "c:\\temp\\winforms.tlz")

    def test1(self):            
        window = self.driver.find_element_by_xpath("//Window")

        accessibleRole = window.get_property("AccessibleRole")
        self.assertEqual(accessibleRole, "Default")

        text = window.get_property("Text")
        self.assertEqual(text, "Test Application")

        self.driver.find_element_by_xpath("//Menu[@caption='Control']").click()
        self.driver.find_element_by_xpath("//MenuItem[@caption='Check box']").click()

        checkBox = self.driver.find_element_by_xpath("//CheckBox[@automationId='chk_Check']")
        self.assertEqual(checkBox.get_property("State"), 2)

        checkBox.click()
        self.assertEqual(checkBox.get_property("State"), 1)

        self.driver.find_element_by_xpath("//PushButton[@automationId='btn_Exit']").click()

    @classmethod
    def tearDownClass(cls):
        cls.driver.execute_script("appdriver:stopTrueLog")
        cls.driver.quit()

if __name__ == "__main__":
    unittest.main()
