package tests;

import models.User;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.*;

public class RegInTests extends TestBase {
    private static final Logger log = Logger.getLogger(RegInTests.class.getName());
    public List<String> invalidEmails;
    public List<String> invalidPasswords;
    User u = new User();
    Random r = new Random();

    public void modelTestForRegIn() {
        app.getHelperUser().openRegInForm();
        app.getHelperUser().fillRegInForm(u);
        app.getHelperUser().checkPolicyXY();
        app.getHelperUser().submitLogin();
    }
    static {
        try {
            LogManager.getLogManager().reset();
            log.setLevel(Level.ALL);
            FileHandler fileHandler = new FileHandler("ReginTestLog.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            log.addHandler(fileHandler);
        } catch (IOException e) {
            log.log(Level.SEVERE, "File logger not working.", e);
        }
    }
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
    public void regInDone() {
        int i = r.nextInt(1000);
        System.out.println(System.currentTimeMillis());
        int z = (int) (System.currentTimeMillis() / i % 3600);
        u.withFirstName("john")
                .withLastName("snow")
                .withEmail("snow" + z + "@gmail.com")
                .withPass("SnowBall123!?");
        modelTestForRegIn();
        Assert.assertEquals(app.getHelperUser().getMessage(), "You are logged in success");
        app.getHelperUser().clickOk();
        app.getHelperUser().click(By.cssSelector (".navigation-link.ng-star-inserted[ng-reflect-query-params='[object Object]']"));
        app.getDriver().navigate().refresh();
    }

    @Test
    public void registrationEmptyName() {
        int i = r.nextInt(1000);
        System.out.println(System.currentTimeMillis());
        int z = (int) (System.currentTimeMillis() / i % 3600);
        u.withFirstName("")
                .withLastName("snow")
                .withEmail("snow" + z + "@gmail.com")
                .withPass("SnowBall123!?");
        log.info("Testing with invalid data  "+u.getFirstName());
        modelTestForRegIn();
        if (app.getHelperUser().isLogged()){
            log.severe("RegIn done with invalid data "+u.getFirstName());
        }else {
            log.info("Failed RegIn "+u.getFirstName());
        }
        Assert.assertFalse(app.getHelperUser().isLogged());
        app.getDriver().navigate().refresh();
    }

    @Test
    public void registrationEmptyLastName() {
        int i = r.nextInt(1000);
        System.out.println(System.currentTimeMillis());
        int z = (int) (System.currentTimeMillis() / i % 3600);
        u.withFirstName("john")
                .withLastName("")
                .withEmail("snow" + z + "@gmail.com")
                .withPass("SnowBall123!?");
        log.info("Testing with invalid data  "+u.getFirstName());
        modelTestForRegIn();
        if (app.getHelperUser().isLogged()){
            log.severe("RegIn done with invalid data "+u.getLastName());
        }else {
            log.info("Failed RegIn  "+u.getLastName());
        }
        Assert.assertFalse(app.getHelperUser().isLogged());
        app.getDriver().navigate().refresh();
    }

   @Test
   public void registrationWrongEmail() {
        for (String invalidEmail : invalidEmails) {
            log.info("Testing with invalid data "+invalidEmail);
            u.withFirstName("john")
                    .withLastName("snow")
                    .withEmail(invalidEmail)
                    .withPass("SnowBall123!?");
            modelTestForRegIn();
            if (app.getHelperUser().isLogged()){
                log.severe("RegIn done with invalid data "+invalidEmail);
            }else {
                log.info("Failed RegIn  "+invalidEmail);
            }
            Assert.assertFalse(app.getHelperUser().isLogged());
            app.getDriver().navigate().refresh();
        }
    }
    @Test
    public void registrationInvalidPass() {
        int i = r.nextInt(1000);
        System.out.println(System.currentTimeMillis());
        int z = (int) (System.currentTimeMillis()/i%3600);
        for (String invalidPass : invalidPasswords) {
            log.info("Testing with invalid data  "+invalidPass);
            u.withFirstName("john")
                    .withLastName("snow")
                    .withEmail("snow" + z + "@gmail.com")
                    .withPass(invalidPass);
            modelTestForRegIn();
            if (app.getHelperUser().isLogged()){
                log.severe("RegIn done with invalid data "+invalidPass);
            }else {
                log.info("pass "+invalidPass);
            }
            Assert.assertFalse(app.getHelperUser().isLogged());
            app.getDriver().navigate().refresh();
        }
    }
    @AfterMethod
    public void postConditions() {
        app.getHelperUser().clickOk();
    }
}
