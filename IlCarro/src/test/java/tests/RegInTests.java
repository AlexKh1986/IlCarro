package tests;

import models.User;
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
            FileHandler fileHandler = new FileHandler("testLog.log", true);
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
        int z = (int) (System.currentTimeMillis() / 1000 % 3600);
        u.withFirstName("john")
                .withLastName("snow")
                .withEmail("snow" + z + "@gmail.com")
                .withPass("SnowBall123!?");
        modelTestForRegIn();
        Assert.assertEquals(app.getHelperUser().getMessage(), "You are logged in success");
    }

    @Test
    public void registrationEmptyName() {
        int i = r.nextInt(1000);
        System.out.println(System.currentTimeMillis());
        int z = (int) (System.currentTimeMillis() / 1000 % 3600);
        u.withFirstName("")
                .withLastName("snow")
                .withEmail("snow" + z + "@gmail.com")
                .withPass("SnowBall123!?");
        modelTestForRegIn();
        Assert.assertFalse(app.getHelperUser().isLogged());
    }

    @Test
    public void registrationEmptyLastName() {
        int i = r.nextInt(1000);
        System.out.println(System.currentTimeMillis());
        int z = (int) (System.currentTimeMillis() / 1000 % 3600);
        u.withFirstName("john")
                .withLastName("")
                .withEmail("snow" + z + "@gmail.com")
                .withPass("SnowBall123!?");
        modelTestForRegIn();
        Assert.assertFalse(app.getHelperUser().isLogged());
    }

   @Test
   public void registrationWrongEmail() {
//        int i = r.nextInt(1000);
//        System.out.println(System.currentTimeMillis());
//        int z = (int) (System.currentTimeMillis()/1000%3600);
        for (String invalidEmail : invalidEmails) {
            log.info("Testing failed "+invalidEmail);
            u.withFirstName("john")
                    .withLastName("snow")
                    .withEmail(invalidEmail)
                    .withPass("SnowBall123!?");
            modelTestForRegIn();
            if (app.getHelperUser().isLogged()){
                log.severe("RegIn done with invalid data "+invalidEmail);
            }else {
                log.info("pass "+invalidEmail);
            }
            Assert.assertFalse(app.getHelperUser().isLogged());
            app.getDriver().navigate().refresh();
        }
    }
    @Test
    public void registrationInvalidPass() {
        int i = r.nextInt(1000);
        System.out.println(System.currentTimeMillis());
        int z = (int) (System.currentTimeMillis()/1000%3600);
        for (String invalidPass : invalidPasswords) {
            log.info("Testing failed "+invalidPass);
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
