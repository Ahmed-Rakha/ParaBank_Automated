package POM_Pages;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;

public class DataGenerator {
    WebDriver driver;
    RegisterPage registerPage;
    Faker faker;
    BillPayPage billPay;
    TransferFundsPage transferFunds;

    public DataGenerator(WebDriver driver, RegisterPage registerPage, BillPayPage billPay, TransferFundsPage transferFunds) {
        this.driver = driver;
        this.registerPage = registerPage;
        this.billPay = billPay;
        this.transferFunds = transferFunds;
    }

    public void generateDataForRegisterForm() {
        faker = new Faker();
        registerPage.setFirstName(faker.name().firstName());
        registerPage.setLastName(faker.name().lastName());
        registerPage.setAddress(faker.address().streetAddress());
        registerPage.setCity(faker.address().city());
        registerPage.setState(faker.address().state());
        registerPage.setZipCode(faker.address().zipCode());
        registerPage.setPhoneNumber(faker.phoneNumber().cellPhone());
        registerPage.setSSN(faker.idNumber().ssnValid());
        registerPage.setUserName(faker.name().username());
        registerPage.setPassword(faker.internet().password());

    }

    public void generateDataForBillPayForm() {
        faker = new Faker();
        billPay.setPayeeName(faker.company().name());
        billPay.setAddress(faker.address().streetAddress());
        billPay.setCity(faker.address().city());
        billPay.setState(faker.address().state());
        billPay.setZipCode(faker.address().zipCode());
        billPay.setPhoneNumber(faker.phoneNumber().cellPhone());
        billPay.setAccountNumber(String.valueOf(faker.number().randomNumber()));
        billPay.setAmount(String.valueOf(faker.number().numberBetween(1, 10000)));
    }


}
