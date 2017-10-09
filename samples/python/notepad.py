import os
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
            desired_capabilities={'app': '%WINDIR%\\notepad.exe'})

        cls.wait = WebDriverWait(cls.driver, 10)

    def setUp(self):        
        self.driver.execute_script("appdriver:startTrueLog",
            os.path.dirname(os.path.abspath(__file__)) + "\\results\\" + __class__.__name__ + ".tlz")

    def test1(self):            
        self.assertEqual(self.driver.title, "Untitled - Notepad")
        self.driver.set_window_rect(400, 500, 300, 200)

        window = self.driver.find_element_by_xpath("//Window")

        textfield = window.find_element_by_xpath("//TextField")
        textfield.clear()
        textfield.send_keys("hello from python!")
        
        self.assertEqual(textfield.text, "hello from python!")

        filemenu = self.driver.find_element_by_xpath("//Menu[@caption='File']")
        filemenu.click()

        self.driver.find_element_by_xpath("//MenuItem[@caption='Exit']").click()

        self.driver.find_element_by_xpath("//PushButton[@caption=\"Don't Save\"]").click()

    @classmethod
    def tearDownClass(cls):
        cls.driver.execute_script("appdriver:stopTrueLog")
        cls.driver.quit()

if __name__ == "__main__":
    unittest.main()
