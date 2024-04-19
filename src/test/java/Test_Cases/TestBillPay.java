package Test_Cases;

import POM_Pages.BillPayPage;
import POM_Pages.DataGenerator;
import POM_Pages.RegisterPage;
import POM_Pages.TransferFundsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestBillPay extends BaseTest {
    RegisterPage registerPage;
    BillPayPage billPay;
    TransferFundsPage transferFunds;

    @DataProvider(name = "billDataValidation")
    public Object[][] validateDataBill() {
        return new Object[][]{
                {}
        };
    }

    @BeforeMethod
    public void generateData() {
        registerPage = new RegisterPage(driver);
        billPay = new BillPayPage(driver);
        DataGenerator dataGenerator = new DataGenerator(driver, registerPage, billPay, transferFunds);
        dataGenerator.generateDataForRegisterForm();
        dataGenerator.generateDataForBillPayForm();
    }

    @Test(priority = 1)
    public void testRedirectionToBillPayForm() {
        registerPage.redirectToRegisterForm().registerNewUser();
        billPay.redirectToBillBayForm();
        Assert.assertTrue(billPay.verifyRedirectionToBillPayForm());
    }

    @Test(priority = 2)
    public void testBillPay() {
        registerPage.redirectToRegisterForm().registerNewUser();
        billPay.redirectToBillBayForm().payBillFromAccountNumber();
        Assert.assertTrue(billPay.verifyBillPaidSuccessfully());
    }
}
