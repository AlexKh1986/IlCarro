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

    @Test
    public void loginSuccess1() {
        user.withEmail("alexkhenkin1986@gmail.com").withPass("Po 12 34 !0 9");
        app.getHelperUser().openLoginRegistrationForm();
        app.getHelperUser().fillLoginRegistrationForm(user);
        app.getHelperUser().submitLogin();
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
        user.withEmail(invalidEmails.toString()).withPass("Po 12 34 !0 9");
        app.getHelperUser().openLoginRegistrationForm();
        app.getHelperUser().fillLoginRegistrationForm(user);
        app.getHelperUser().submitLogin();
        Assert.assertFalse(app.getHelperUser().isLogged());
    }

    @Test
    public void wrongPassLogin() {
        user.withEmail("alexkhenkin1986@gmail.com").withPass(invalidPasswords.toString());
        app.getHelperUser().openLoginRegistrationForm();
        app.getHelperUser().fillLoginRegistrationForm(user);
        app.getHelperUser().submitLogin();
        Assert.assertFalse(app.getHelperUser().isLogged());
    }

    @Test
    public void unregUserLogIn() {
        user.withEmail("alex1986@gmail.com").withPass("Po 12 34 !0 9");
        app.getHelperUser().openLoginRegistrationForm();
        app.getHelperUser().fillLoginRegistrationForm(user);
        app.getHelperUser().submitLogin();
        Assert.assertFalse(app.getHelperUser().isLogged());
    }

    @AfterMethod
    public void postCondition() {
        app.getHelperUser().clickOk();
    }
}
