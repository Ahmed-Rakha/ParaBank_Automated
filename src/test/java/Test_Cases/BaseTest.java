package Test_Cases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BaseTest {

    WebDriver driver;
    String URL = "https://parabank.parasoft.com/parabank/index.htm";

    @BeforeMethod
    @Test
    public void setUp() {
        driver = new ChromeDriver();
        driver.navigate().to(URL);
    }

    @AfterMethod
    @Test
    public void tearDown() {
        driver.quit();
    }
}
