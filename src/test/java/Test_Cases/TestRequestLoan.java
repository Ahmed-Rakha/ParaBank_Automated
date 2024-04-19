package Test_Cases;

import POM_Pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestRequestLoan extends BaseTest {
    RegisterPage registerPage;
    OpenNewAccountPage openNewAccount;
    BillPayPage billPay;
    TransferFundsPage transferFunds;
    RequestLoanPage requestLoan;

    @BeforeMethod
    public void generateData() {
        registerPage = new RegisterPage(driver);
        openNewAccount = new OpenNewAccountPage(driver);
        billPay = new BillPayPage(driver);
        transferFunds = new TransferFundsPage(driver);
        requestLoan = new RequestLoanPage(driver);
        DataGenerator dataGenerator = new DataGenerator(driver, registerPage, billPay, transferFunds);
        dataGenerator.generateDataForRegisterForm();

    }

    // test data for input validation
    // Rules for acceptance criteria:
    // 1- All the fields must be numbers and with NO Blank.
    // 2- Loan amount must be bigger than the down payment.
    // 3- Minimum down Payment accepted => 10% of the total loan amount
    // 4- The process must be rejected of the down Payment is exceeded a particular percentage.


    @DataProvider(name = "validateDataInput")
    public Object[][] setDataValidation() {
        return new Object[][]{
//              loanAmountValue - downPaymentValue - isValidInputs - isLoanApproved
                {"100", "20", true, true}, // happy scenario
                {"100", "10", true, true}, // happy scenario
                {"text", "100", false, false}, // invalid 1st input => text
                {"100", "text", false, false}, // invalid 2nd input = > text
                {"", "100", false, false}, // invalid 1st input => Blank
                {"100", "", false, false}, // invalid 2nd input => Blank
                {"5", "100", true, false}, // invalid 1st input => loan less than the down payment. // bug its accepted
                {"100", "5", true, false}, // invalid 2nd input => down Payment less than 10% of total loan // bug
                {"100", "9.9", true, false}, // invalid 2nd input => down Payment less than 10% of total loan // bug
                {"100", "100", true, false} // invalid 2nd input => down Payment is more than a particular percentage.// bug accepted
        };
    }

    @Test(priority = 1)
    public void testRedirectionToLoanForm() {
        registerPage.registerNewUser();
        requestLoan.redirectToLoanForm();
        Assert.assertTrue(requestLoan.verifyRedirectedToLoanForm());
    }

    @Test(dataProvider = "validateDataInput", priority = 3)
    public void testRequestLoan(String loanAmount, String downPayment, boolean expectedLoanProcessedResult, boolean expectedLoanStatusResult) throws ClassNotFoundException {
        registerPage.registerNewUser();
        requestLoan.redirectToLoanForm().requestLoan(loanAmount, downPayment);
        if (expectedLoanProcessedResult) {
            Assert.assertTrue(requestLoan.verifyRequestLoanProcessedSuccessfully(), "Loan request submitted successfully !");
            if (expectedLoanStatusResult)
                Assert.assertTrue(requestLoan.verifyLoanApprovalStatus(), "Loan status Approved !");
            else Assert.assertTrue(requestLoan.verifyLoanDeniedStatus(), "Loan status Denied !");
        } else Assert.assertTrue(requestLoan.verifyInvalidDataInserted(), "Loan request not submitted properly");

    }
}
