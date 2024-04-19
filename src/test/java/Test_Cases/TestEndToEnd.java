package Test_Cases;

import POM_Pages.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestEndToEnd extends BaseTest {
    RegisterPage registerPage;
    BillPayPage billPay;
    DataGenerator dataGenerator;
    TransferFundsPage transferFunds;

    @BeforeMethod
    public void generateData() {
        registerPage = new RegisterPage(driver);
        billPay = new BillPayPage(driver);
        dataGenerator = new DataGenerator(driver, registerPage, billPay, transferFunds);
        dataGenerator.generateDataForRegisterForm();
        dataGenerator.generateDataForBillPayForm();
    }

    @Test
    public void testEndToEnd() {

        new EndToEndFacade(driver, registerPage, billPay)
                .registerNewUser()
                .openNewAccount()
                .requestLoan()
                .transferFunds()
                .payBillFromAccountNumber()
                .updateContactInfo();
    }
}
