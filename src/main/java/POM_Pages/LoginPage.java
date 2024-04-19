package POM_Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    Logger logger = LogManager.getLogger();
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    //    Locate elements on Login Page.
    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By loginBtn = By.xpath("//input[contains(@value,\"Log In\")]");

    //    Actions on the Login Page.

    public void login() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys("haley.satterfield");
            wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys("ds7v0p5l3ek01");
            wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();

        } catch (Exception e) {
            logger.error("Error occurs while trying to Login => ", e);
        }
    }
}
