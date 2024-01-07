package tests;

import models.User;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.*;

public class LoginTest extends TestBase {
    User user = new User();
    private List<String> invalidEmails;
    private List<String> invalidPasswords;

    @BeforeMethod
    public void preCondition() {
        if (app.getHelperUser().isLogged()) {
            app.getHelperUser().logout();
        }
        app.getHelperUser().generateAndSaveTestData();
        invalidEmails = app.getHelperUser().readInvalidEmailsFromFile("invalid_emails.txt");
        invalidPasswords = app.getHelperUser().readInvalidPasswordsFromFile("invalid_passwords.txt");
    }

    private void modelLogInTest(){
        app.getHelperUser().openLoginRegistrationForm();
        app.getHelperUser().fillLoginRegistrationForm(user);
        app.getHelperUser().submitLogin();
    }
    @Test
    public void loginSuccess1() {
        user.withEmail("alexkhenkin1986@gmail.com").withPass("Po 12 34 !0 9");
        modelLogInTest();
        Assert.assertTrue(app.getHelperUser().isLogged());
    }

    @Test
    public void loginSuccess() {
        app.getHelperUser().openLoginRegistrationForm();
        app.getHelperUser().fillLoginRegistrationForm("alexkhenkin1986@gmail.com", "Po 12 34 !0 9");
        app.getHelperUser().submitLogin();
        Assert.assertTrue(app.getHelperUser().isLogged());
    }

    @Test
    public void wrongEmailLogIn() {
        for (String invalidEmail : invalidEmails) {
            user.withEmail(invalidEmail).withPass("Po 12 34 !0 9");
            modelLogInTest();
            Assert.assertFalse(app.getHelperUser().isLogged());
            app.getDriver().navigate().refresh();
        }
    }

    @Test
    public void wrongPassLogin() {
        for (String invalidPass : invalidPasswords) {
            user.withEmail("alexkhenkin1986@gmail.com").withPass(invalidPass);
            modelLogInTest();
            Assert.assertFalse(app.getHelperUser().isLogged());
            app.getDriver().navigate().refresh();
        }
    }

    @Test
    public void unregUserLogIn() {
        user.withEmail("alex1986@gmail.com").withPass("Po 12 34 !0 9");
        modelLogInTest();
        Assert.assertFalse(app.getHelperUser().isLogged());
        app.getDriver().navigate().refresh();
    }


    @AfterMethod
    public void postCondition() {
        app.getHelperUser().clickOk();
    }
}
