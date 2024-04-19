package Test_Cases;

import POM_Pages.LoginPage;
import org.testng.annotations.Test;

public class TestLogin extends BaseTest {
    @Test
    public void testLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login();
    }
}
