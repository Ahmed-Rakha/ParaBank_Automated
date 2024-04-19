package Test_Cases;

import POM_Pages.BillPayPage;
import POM_Pages.DataGenerator;
import POM_Pages.RegisterPage;
import POM_Pages.TransferFundsPage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestRegister extends BaseTest {
    RegisterPage registerPage;
    BillPayPage billPay;
    TransferFundsPage transferFunds;

    @Test(priority = 1)
    public void testRedirectionToRegisterForm() {
        registerPage = new RegisterPage(driver);
        billPay = new BillPayPage(driver);
        registerPage.redirectToRegisterForm();
        Assert.assertTrue(registerPage.verifyRedirectionToRegisterForm());
    }

    @Test(priority = 2)
    public void testRegister() {
        registerPage = new RegisterPage(driver);
        DataGenerator dataGenerator = new DataGenerator(driver, registerPage, billPay, transferFunds);
        dataGenerator.generateDataForRegisterForm();
        registerPage.registerNewUser();
        Assert.assertTrue(registerPage.verifyUserRegisteredSuccessfully());
    }
}
