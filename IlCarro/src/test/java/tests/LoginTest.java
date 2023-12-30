package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends TestBase {
    @BeforeMethod
    public void preCondition() {
        if (app.getHelperUser().isLogged()) {
            app.getHelperUser().logout();
        }
    }

    @Test
    public void loginSuccess() {
        app.getHelperUser().openLoginRegistrationForm();
        app.getHelperUser().fillLoginRegistrationForm("alexkhenkin1986@gmail.com", "Po 12 34 !0 9");
        app.getHelperUser().submitLogin();
        Assert.assertTrue(app.getHelperUser().isLogged());
    }
}
