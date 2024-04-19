package POM_Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TransferFundsPage {
    Logger logger = LogManager.getLogger();
    private final WebDriver driver;
    private final String transferFunds_URL = "https://parabank.parasoft.com/parabank/transfer.htm";

    public TransferFundsPage(WebDriver driver) {
        this.driver = driver;
    }

    //    Locate elements on the Transfer Funds Page.
    private final By transferFundBtn = By.linkText("Transfer Funds");
    private final By amountLocator = By.id("amount");
    private final By fromAccountLocator = By.id("fromAccountId");
    private final By toAccountLocator = By.id("toAccountId");
    private final By submitTransferBtn = By.xpath("//input[@value=\"Transfer\"]");
    private final By assertOnRedirectionToTransferFundsForm = By.xpath("//h1[text()=\"Transfer Funds\"]");
    private final By assertOnTransferredCompleted = By.xpath("//h1[text()=\"Transfer Complete!\"]");


//  Actions on Transfer Funds Page.

    public TransferFundsPage redirectToTransferFundsForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            wait.until(ExpectedConditions.elementToBeClickable(transferFundBtn)).click();
        } catch (Exception e) {
            logger.error("Error occurs while trying to click on Transfer Funds Button => ", e);
        }
        return this;
    }

    public void transferFunds() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        try {

            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(amountLocator)).click(driver.findElement(amountLocator))
                    .keyDown(Keys.NUMPAD2)
                    .keyDown(Keys.NUMPAD3)
                    .keyDown(Keys.NUMPAD4)
                    .keyDown(Keys.NUMPAD5)
                    .build().perform();
            Select fromAccount = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(fromAccountLocator)));
            fromAccount.selectByIndex(0);
            Select toAccount = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(toAccountLocator)));
            toAccount.selectByIndex(0);
            new Actions(driver).moveToElement(driver.findElement(submitTransferBtn)).click().perform();

        } catch (Exception e) {
            logger.error("Error occurs while trying to transfer funds => ", e);
        }
    }

    //    Assertions
    public boolean verifyRedirectionToTransferFundsForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(assertOnRedirectionToTransferFundsForm)).isDisplayed();
    }

    public boolean verifytransferCompletedSuccessfully() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(assertOnTransferredCompleted)).isDisplayed();
    }

}
