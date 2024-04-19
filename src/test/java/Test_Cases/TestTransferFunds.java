package Test_Cases;

import POM_Pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestTransferFunds extends BaseTest {
    RegisterPage registerPage;
    OpenNewAccountPage openNewAccount;
    BillPayPage billPay;
    TransferFundsPage transferFunds;

    @BeforeMethod
    public void generateData() {
        registerPage = new RegisterPage(driver);
        openNewAccount = new OpenNewAccountPage(driver);
        billPay = new BillPayPage(driver);
        transferFunds = new TransferFundsPage(driver);
        DataGenerator dataGenerator = new DataGenerator(driver, registerPage, billPay, transferFunds);
        dataGenerator.generateDataForRegisterForm();

    }

    @Test(priority = 1)
    public void testRedirectionToTransferFundsForm() {
        registerPage.redirectToRegisterForm().registerNewUser();
        transferFunds.redirectToTransferFundsForm();
        Assert.assertTrue(transferFunds.verifyRedirectionToTransferFundsForm());
    }

    @Test(priority = 2)
    public void testTransferFunds() {
        registerPage.redirectToRegisterForm().registerNewUser();
        transferFunds.redirectToTransferFundsForm().transferFunds();
        Assert.assertTrue(transferFunds.verifytransferCompletedSuccessfully());
    }
}
