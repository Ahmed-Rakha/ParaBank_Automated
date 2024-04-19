package POM_Pages;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.apache.logging.log4j.Logger;

public class BillPayPage {
    private final Logger logger = LogManager.getLogger();
    private final WebDriver driver;
    DatabaseConnector databaseConnector;
    private final String billPay_URL = "https://parabank.parasoft.com/parabank/billpay.htm";

    public BillPayPage(WebDriver driver) {
        this.driver = driver;
        this.databaseConnector = new DatabaseConnector();
    }

    private String payeeName;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String phoneNumber;
    private String accountNumber; // max number 9
    private String amount;

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    //    locate elements on BillPay Page.
    private final By payBillBtn = By.linkText("Bill Pay");
    private final By payeeNameField = By.name("payee.name");
    private final By addressField = By.name("payee.address.street");
    private final By cityField = By.name("payee.address.city");
    private final By stateField = By.name("payee.address.state");
    private final By zipCodeField = By.name("payee.address.zipCode");
    private final By phoneNumberField = By.name("payee.phoneNumber");
    private final By accountField = By.name("payee.accountNumber");
    private final By verifyAccountField = By.name("verifyAccount");
    private final By amountField = By.name("amount");
    private final By fromAccountField = By.name("fromAccountId");
    private final By submitBillBtn = By.xpath("//input[@value=\"Send Payment\"]");
    private final By assertOnRedirectionToBillPayForm = By.xpath("//h1[contains(text(),\"Bill Payment Service\")]");
    private final By assertOnSuccessfulBillPay = By.xpath("//h1[contains(text(),\"Bill Payment Complete\")]");


//    Actions on BillPay Page.

    public BillPayPage redirectToBillBayForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            wait.until(ExpectedConditions.elementToBeClickable(payBillBtn)).click();
        } catch (Exception e) {
            logger.error("Error occurs while trying to click on Bill Pay Button => ", e);
        }
        return this;
    }

    public void payBillFromAccountNumber() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {

            wait.until(ExpectedConditions.visibilityOfElementLocated(payeeNameField)).sendKeys(payeeName);
            System.out.println(payeeName);
            wait.until(ExpectedConditions.visibilityOfElementLocated(addressField)).sendKeys(address);
            System.out.println(address);
            wait.until(ExpectedConditions.visibilityOfElementLocated(cityField)).sendKeys(city);
            System.out.println(city);
            wait.until(ExpectedConditions.visibilityOfElementLocated(stateField)).sendKeys(state);
            System.out.println(state);
            wait.until(ExpectedConditions.visibilityOfElementLocated(zipCodeField)).sendKeys(zipCode);
            System.out.println(zipCode);
            wait.until(ExpectedConditions.visibilityOfElementLocated(phoneNumberField)).sendKeys(phoneNumber);
            System.out.println(phoneNumber);
            wait.until(ExpectedConditions.visibilityOfElementLocated(accountField)).sendKeys(accountNumber);
            System.out.println(accountNumber);
            wait.until(ExpectedConditions.visibilityOfElementLocated(verifyAccountField)).sendKeys(accountNumber);
            wait.until(ExpectedConditions.visibilityOfElementLocated(amountField)).sendKeys(amount);
            System.out.println(amount);
            Select select = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(fromAccountField)));
            select.selectByIndex(0);
            wait.until(ExpectedConditions.elementToBeClickable(submitBillBtn)).click();

            if (driver.findElement(assertOnSuccessfulBillPay).isDisplayed()) {
                databaseConnector.insertBillData(payeeName, address, city, state, zipCode, phoneNumber, accountNumber, amount);
            }
        } catch (Exception e) {
            logger.error("Error occurs while paying the bill  => ", e);
        }

    }

    //   Assertions
    public boolean verifyRedirectionToBillPayForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(assertOnRedirectionToBillPayForm)).isDisplayed();
    }

    public boolean verifyBillPaidSuccessfully() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(assertOnSuccessfulBillPay)).isDisplayed();

    }

}
