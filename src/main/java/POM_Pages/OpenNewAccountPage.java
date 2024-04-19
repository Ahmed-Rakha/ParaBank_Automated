package POM_Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OpenNewAccountPage {
    WebDriver driver;
    Logger logger = LogManager.getLogger();
    private final String openNewAccount_URL = "https://parabank.parasoft.com/parabank/openaccount.htm";

    public OpenNewAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    //    locate elements on Open New Account Page.
    private final By openNewAccountBtn = By.linkText("Open New Account");
    private final By selectAccountType = By.id("type");
    private final By selectAccountNumber = By.id("fromAccountId");
    private final By submitNewAccountForm = By.xpath("//input[@value=\"Open New Account\"]");
    private final By assertOnAccountOpened = By.xpath("//h1[text()=\"Account Opened!\"]");
    private final By assertOnRedirectionToNewAccountForm = By.xpath("//h1[text()=\"Open New Account\"]");

//    Actions on Open New Account Page

    public OpenNewAccountPage redirectToOpenNewAccountForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            wait.until(ExpectedConditions.elementToBeClickable(openNewAccountBtn)).click();
        } catch (Exception e) {
            logger.error("Error occurs while trying to click on Open New Account Button => ", e);
        }
        return this;
    }

    public void openNewAccount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            if (!driver.getCurrentUrl().equals(openNewAccount_URL)) {
                wait.until(ExpectedConditions.elementToBeClickable(openNewAccountBtn)).click();
            }
            Select accountType = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(selectAccountType)));
            accountType.selectByIndex(1);
            Select accountNumber = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(selectAccountNumber)));
            accountNumber.selectByIndex(0);
            new Actions(driver).moveToElement(driver.findElement(submitNewAccountForm)).click().perform();

        } catch (Exception e) {
            logger.error("Error occurs while trying to Open New Account => ", e);
        }
    }

    //    Assertions
    public boolean verifyRedirectionToOpenNewAccountForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(assertOnRedirectionToNewAccountForm)).isDisplayed();
    }

    public boolean verifyNewAccountOpenedSuccessfully() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(assertOnAccountOpened)).isDisplayed();
    }
}
