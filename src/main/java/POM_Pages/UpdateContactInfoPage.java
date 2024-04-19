package POM_Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class UpdateContactInfoPage {
    Logger logger = LogManager.getLogger();
    RegisterPage registerPage;
    DatabaseConnector databaseConnector = new DatabaseConnector();
    private final WebDriver driver;
    private final String updateContact_URL = "https://parabank.parasoft.com/parabank/updateprofile.htm";

    public UpdateContactInfoPage(WebDriver driver, RegisterPage registerPage) {
        this.driver = driver;
        this.registerPage = registerPage;
    }

    //    Locate elements on the Contact Info Page.
    private final By updateContactInfoBtn = By.linkText("Update Contact Info");
    private final By firstNameField = By.id("customer.firstName");
    private final By lastNameField = By.id("customer.lastName");
    private final By addressField = By.id("customer.address.street");
    private final By cityField = By.id("customer.address.city");
    private final By stateField = By.id("customer.address.state");
    private final By zipCodeField = By.id("customer.address.zipCode");
    private final By phoneNumberField = By.id("customer.phoneNumber");
    private final By submitBtn = By.xpath("//input[contains(@value,\"Update Profile\")]");
    private final By inputsLocators = By.xpath("//table[@class=\"form2\"]/tbody/tr/td[2]/input[@type=\"text\"]");
    private final By assertOnRedirectionToUpdateContactInfoForm = By.xpath("//h1[text()=\"Update Profile\"]");
    private final By assertOnSuccessfullyUpdatedInfo = By.xpath("//h1[contains(text(),\"Profile Updated\")]");
    private final By assertOnInvalidDataEntry = By.className(".error.ng-scope");

    //    Actions on the Contact Info Page.

    public UpdateContactInfoPage redirectToUpdateContactInfoForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(updateContactInfoBtn)).click();
        } catch (Exception e) {
            logger.error("Error occurs while trying to click on Update Contact Info Button => ", e);
        }
        return this;
    }

    public List<String> updateContactInfo(@org.jetbrains.annotations.NotNull Object[][] tokens) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<String> getListOfExpectedContactInfoUpdates = null;
        try {

            Thread.sleep(2000);
            getListOfExpectedContactInfoUpdates = driver.findElements(inputsLocators).stream().map(ele -> ele.getText()).toList();
            for (Object[] i : tokens) {
                By fieldLocator = (By) i[0];
                String updatedValue = (String) i[1];
                String targetColumn = (String) i[2];
                WebElement targetElement = wait.until(ExpectedConditions.visibilityOfElementLocated(fieldLocator));
                targetElement.clear();
                targetElement.sendKeys(updatedValue);
                String getUpdatedValue = targetElement.getText();
                new Actions(driver).moveToElement(driver.findElement(submitBtn)).click(driver.findElement(submitBtn)).perform();
                databaseConnector.updateContactInfo(targetColumn, getUpdatedValue, registerPage.getUserName());
            }
        } catch (Exception e) {
            logger.error("Error occurs while trying to  Update Contact Info  => ", e);

        }

        return getListOfExpectedContactInfoUpdates;
    }

    // Assertions
    public boolean verifyRedirectionToUpdateContactInfoForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(assertOnRedirectionToUpdateContactInfoForm)).isDisplayed();
    }

    public List<String> getUpdatedContactInfo() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.navigate().refresh();
        wait.until(ExpectedConditions.elementToBeClickable(updateContactInfoBtn));
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn));
        return driver.findElements(inputsLocators).stream().map(WebElement::getText).toList();
    }

    public boolean verifyInvalidDataInserted() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(assertOnInvalidDataEntry)).isDisplayed();
    }

    //    Getters
    public By getFirstNameField() {
        return firstNameField;
    }

    public By getLastNameField() {
        return lastNameField;
    }

    public By getAddressField() {
        return addressField;
    }

    public By getCityField() {
        return cityField;
    }

    public By getStateField() {
        return stateField;
    }

    public By getZipCodeField() {
        return zipCodeField;
    }

    public By getPhoneNumberField() {
        return phoneNumberField;
    }
}
