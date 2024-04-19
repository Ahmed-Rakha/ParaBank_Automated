package Test_Cases;

import POM_Pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestOpenNewAccount extends BaseTest {
    RegisterPage registerPage;
    OpenNewAccountPage openNewAccount;
    BillPayPage billPay;
    TransferFundsPage transferFunds;

    @BeforeMethod
    public void generateData() {
        registerPage = new RegisterPage(driver);
        openNewAccount = new OpenNewAccountPage(driver);
        billPay = new BillPayPage(driver);
        DataGenerator dataGenerator = new DataGenerator(driver, registerPage, billPay, transferFunds);
        dataGenerator.generateDataForRegisterForm();
    }

    @Test(priority = 1)
    public void testRedirectionToBillPayForm() {
        registerPage.redirectToRegisterForm().registerNewUser();
        openNewAccount.redirectToOpenNewAccountForm();
        Assert.assertTrue(openNewAccount.verifyRedirectionToOpenNewAccountForm());
    }

    @Test(priority = 2)
    public void testBillPay() {
        registerPage.redirectToRegisterForm().registerNewUser();
        openNewAccount.openNewAccount();
        Assert.assertTrue(openNewAccount.verifyNewAccountOpenedSuccessfully());
    }
}
