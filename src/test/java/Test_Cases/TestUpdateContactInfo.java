package Test_Cases;

import POM_Pages.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class TestUpdateContactInfo extends BaseTest {
    RegisterPage registerPage;
    OpenNewAccountPage openNewAccount;
    BillPayPage billPay;
    TransferFundsPage transferFunds;
    RequestLoanPage requestLoan;
    UpdateContactInfoPage updateContactInfo;

    //Names of Columns defined in the database and editable by this test case:=>
    String firstName = "first_name";
    String lastName = "last_name";
    String streetAddress = "street_Address";
    String city = "city";
    String country = "country";
    String zipCode = "zipCode";
    String phoneNumber = "phoneNumber";

    @DataProvider(name = "dataEntry")
    public Object[][] validateDataEntry() {
        return new Object[][]{
                {By.id("customer.firstName"), "Jose", firstName, true},
                {By.id("customer.firstName"), "", firstName, false}, // BUG
                {By.id("customer.firstName"), "12345", firstName, false}, // BUG
                {By.id("customer.firstName"), "jo2se", firstName, false}, // BUG
                {By.id("customer.address.zipCode"), "1234", zipCode, true},
                {By.id("customer.address.zipCode"), "abcde", zipCode, false}// BUG and the database will be affected too
        };
    }


    @BeforeMethod
    public void generateData() {
        registerPage = new RegisterPage(driver);
        openNewAccount = new OpenNewAccountPage(driver);
        billPay = new BillPayPage(driver);
        transferFunds = new TransferFundsPage(driver);
        requestLoan = new RequestLoanPage(driver);
        updateContactInfo = new UpdateContactInfoPage(driver, registerPage);
        DataGenerator dataGenerator = new DataGenerator(driver, registerPage, billPay, transferFunds);
        dataGenerator.generateDataForRegisterForm();

    }

    @Test(priority = 1)
    public void testRedirectionToUpdateContactInfoForm() {
        registerPage.redirectToRegisterForm().registerNewUser();
        updateContactInfo.redirectToUpdateContactInfoForm();
        Assert.assertTrue(updateContactInfo.verifyRedirectionToUpdateContactInfoForm());
    }


    @Test(priority = 2)
    public void testUpdateContactInfo() {
        // Define the data to be updated as a 2D array of Objects
        // {fieldLocator, new value, column affected in the database}
        Object[][] updateData = {
                {updateContactInfo.getFirstNameField(), "Martinez", firstName},
        };
        registerPage.redirectToRegisterForm().registerNewUser();
        List<String> getListOfExpectedContactInfoUpdated = updateContactInfo.redirectToUpdateContactInfoForm().updateContactInfo(updateData);
        List<String> getListOfContactInfoBeingUpdated = updateContactInfo.redirectToUpdateContactInfoForm().getUpdatedContactInfo();
        Assert.assertEquals(getListOfExpectedContactInfoUpdated, getListOfContactInfoBeingUpdated);
    }

    //     validate different type of inputs[text, numeric] within the same test case.
    @Test(dataProvider = "dataEntry")
    public void testDataEntry(By fieldLocator, String updatedValue, String targetColumn, boolean expectedResult) {
        Object[][] updateData = {
                {fieldLocator, updatedValue, targetColumn},
        };
        registerPage.redirectToRegisterForm().registerNewUser();
        List<String> getListOfExpectedContactInfoUpdated = updateContactInfo.redirectToUpdateContactInfoForm().updateContactInfo(updateData);
        List<String> getListOfContactInfoBeingUpdated = updateContactInfo.redirectToUpdateContactInfoForm().getUpdatedContactInfo();
        if (expectedResult) {
            Assert.assertEquals(getListOfExpectedContactInfoUpdated, getListOfContactInfoBeingUpdated, "Contact info update should be successful");
        } else {
            Assert.assertTrue(updateContactInfo.verifyInvalidDataInserted(), "Invalid data insertion detected");
        }
    }
}
