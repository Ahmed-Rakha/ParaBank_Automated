package POM_Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RequestLoanPage {
    Logger logger = LogManager.getLogger();
    WebDriver driver;

    public RequestLoanPage(WebDriver driver) {
        this.driver = driver;
    }

    //    Locate elements on Request Loan Page.
    private final By requestLoanBtn = By.linkText("Request Loan");
    private final By assertOnRedirectionToLoanForm = By.xpath("//h1[contains(text(),\"Apply for a Loan\")]");
    private final By loanAmountField = By.id("amount");
    private final By downPaymentField = By.id("downPayment");
    private final By selectAccount = By.id("fromAccountId");
    private final By submitLoanBtn = By.xpath("//input[contains(@value,\"Apply Now\")]");
    private final By assertOnRequestLoan = By.xpath("// h1[contains(text(),\"Loan Request Processed\")]");
    private final By errorMessage = By.xpath("//h1[contains(text(),\"Error\")]");
    private final By requestStatus = By.id("loanStatus");
    private final By requestApproved = By.xpath("//td[contains(text(),\"Approved\")]");
    private final By requestDenied = By.xpath("//td[contains(text(),\"Denied\")]");

    //    Actions on Loan Page
    public RequestLoanPage redirectToLoanForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(requestLoanBtn)).click();
        } catch (Exception e) {
            logger.error("Error occurs while trying to click on Request Laon Button => ", e);
        }
        return this;
    }

    public void requestLoan(String loanAmount, String downPayment) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(loanAmountField)).sendKeys(loanAmount);
            System.out.println("LoanAmount => " + loanAmount);
            wait.until(ExpectedConditions.visibilityOfElementLocated(downPaymentField)).sendKeys(downPayment);
            System.out.println("downPayment => " + downPayment);
            Select select = new Select(wait.until(ExpectedConditions.elementToBeClickable(selectAccount)));
            select.selectByIndex(0);
            wait.until(ExpectedConditions.visibilityOfElementLocated(submitLoanBtn)).click();
        } catch (Exception e) {
            logger.error("Error occurs while trying to request loan => ", e);
        }
    }

    //  Assertions
    public boolean verifyRedirectedToLoanForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(assertOnRedirectionToLoanForm)).isDisplayed();
    }

    public boolean verifyRequestLoanProcessedSuccessfully() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(assertOnRequestLoan)).isDisplayed();
    }

    public boolean verifyInvalidDataInserted() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
    }

    public boolean verifyLoanApprovalStatus() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(requestApproved)).isDisplayed();
    }

    public boolean verifyLoanDeniedStatus() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(requestDenied)).isDisplayed();
    }

}
