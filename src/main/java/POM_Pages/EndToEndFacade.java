package POM_Pages;


import org.openqa.selenium.WebDriver;

public class EndToEndFacade {
    private final WebDriver driver;
    private final RegisterPage registerPage;
    private final LoginPage loginPage;
    private final OpenNewAccountPage openNewAccountPage;
    private final RequestLoanPage requestLoanPage;
    private final TransferFundsPage transferFundsPage;
    private final UpdateContactInfoPage updateContactInfoPage;
    private final BillPayPage billPayPage;

    public EndToEndFacade(WebDriver driver, RegisterPage registerPage, BillPayPage billPayPage) {
        this.driver = driver;
        this.registerPage = registerPage;
        this.loginPage = new LoginPage(driver);
        this.openNewAccountPage = new OpenNewAccountPage(driver);
        this.transferFundsPage = new TransferFundsPage(driver);
        this.requestLoanPage = new RequestLoanPage(driver);
        this.updateContactInfoPage = new UpdateContactInfoPage(driver, registerPage);
        this.billPayPage = billPayPage;
    }


    public EndToEndFacade registerNewUser() {
        registerPage.redirectToRegisterForm().registerNewUser();
        return this;
    }

    public EndToEndFacade openNewAccount() {
        openNewAccountPage.redirectToOpenNewAccountForm().openNewAccount();
        return this;
    }

    public EndToEndFacade transferFunds() {
        transferFundsPage.redirectToTransferFundsForm().transferFunds();
        return this;
    }

    public EndToEndFacade requestLoan() {
        requestLoanPage.redirectToLoanForm().requestLoan("100", "20");
        return this;
    }

    public EndToEndFacade payBillFromAccountNumber() {
        billPayPage.redirectToBillBayForm().payBillFromAccountNumber();
        return this;

    }

    public void updateContactInfo() {
        Object[][] updateData = {
                {updateContactInfoPage.getFirstNameField(), "Martinez", "first_name"},
        };
        updateContactInfoPage.redirectToUpdateContactInfoForm().updateContactInfo(updateData);
    }
}