package POM_Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class RegisterPage {
    private final Logger logger = LogManager.getLogger();
    private final WebDriver driver;
    DatabaseConnector databaseConnector;
    private final String registerPage_URL = "https://parabank.parasoft.com/parabank/register.htm";

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.databaseConnector = new DatabaseConnector();
    }

    //    Data Declaration:
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String phoneNumber;
    private String SSN;
    private String userName;
    private String password;
//    Set data

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // locate elements on Register page.
    private final By registerBtn = By.linkText("Register");
    private final By firstNameField = By.id("customer.firstName");
    private final By lastNameField = By.id("customer.lastName");
    private final By addressField = By.id("customer.address.street");
    private final By cityField = By.id("customer.address.city");
    private final By stateField = By.id("customer.address.state");
    private final By zipCodeField = By.id("customer.address.zipCode");
    private final By phoneField = By.id("customer.phoneNumber");
    private final By ssnField = By.id("customer.ssn");
    private final By userNameField = By.id("customer.username");
    private final By passwordField = By.id("customer.password");
    private final By repeatPasswordField = By.id("repeatedPassword");
    private final By submitFormBtn = By.xpath("(//input[@class=\"button\"])[2]");
    private final By assertOnRedirectionToRegisterForm = By.xpath("//h1[text()=\"Signing up is easy!\"]");
    private final By assertOnSuccessfullyRegister = By.xpath("//h1[contains(text(),\"Welcome\")]");

//    Actions on the Register page.

    public RegisterPage redirectToRegisterForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(registerBtn)).click();

        } catch (Exception e) {
            logger.error("Error occurs while trying to click on Register Button => ", e);
        }
        return this;
    }

    public void registerNewUser() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            if (!driver.getCurrentUrl().equals(registerPage_URL)) {
                wait.until(ExpectedConditions.elementToBeClickable(registerBtn)).click();
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField)).sendKeys(firstName);
            wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameField)).sendKeys(lastName);
            wait.until(ExpectedConditions.visibilityOfElementLocated(addressField)).sendKeys(address);
            wait.until(ExpectedConditions.visibilityOfElementLocated(cityField)).sendKeys(city);
            wait.until(ExpectedConditions.visibilityOfElementLocated(stateField)).sendKeys(state);
            wait.until(ExpectedConditions.visibilityOfElementLocated(zipCodeField)).sendKeys(zipCode);
            wait.until(ExpectedConditions.visibilityOfElementLocated(phoneField)).sendKeys(phoneNumber);
            wait.until(ExpectedConditions.visibilityOfElementLocated(ssnField)).sendKeys(SSN);
            wait.until(ExpectedConditions.visibilityOfElementLocated(userNameField)).sendKeys(userName);
            wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
            wait.until(ExpectedConditions.visibilityOfElementLocated(repeatPasswordField)).sendKeys(password);
            wait.until(ExpectedConditions.elementToBeClickable(submitFormBtn)).click();

            if (driver.findElement(assertOnSuccessfullyRegister).isDisplayed()) {
                databaseConnector.insertUsersData(firstName, lastName, address, city, state, zipCode, phoneNumber, SSN, userName, password);
            }

        } catch (Exception e) {
            logger.error("Error occurs while registering the user => ", e);
        }

    }

    // Assertions
    public boolean verifyRedirectionToRegisterForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(assertOnRedirectionToRegisterForm)).isDisplayed();
    }

    public boolean verifyUserRegisteredSuccessfully() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(assertOnSuccessfullyRegister)).isDisplayed();
    }
}
