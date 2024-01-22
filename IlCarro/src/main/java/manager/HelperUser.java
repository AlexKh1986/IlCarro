package manager;

import models.User;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelperUser extends HelperBase {
    User user = new User();
    private final Random random = new Random();
    public HelperUser(WebDriver wd) {
        super(wd);
    }
    public void openLoginRegistrationForm() {
        clickButtonByNumber("Log in", 1);
    }
    public void fillLoginRegistrationForm(String Email, String Password) {
        type(By.id("email"), Email);
        type(By.id("password"), Password);
    }
    public void fillLoginRegistrationForm(User user) {
        type(By.id("email"), user.getEmail());
        type(By.id("password"), user.getPass());
    }
    public void submitLogin() {
        WebElement button = wd.findElement(By.xpath("//button[contains(text(), 'Y’alla!')]"));
        button.click();
    }
    public boolean isLogged() {
        return isElementPresent(By.xpath("//*[.=' Logout ']"));
    }
    public void logout() {
        click("Logout");
    }

//    public boolean isAlertPresent(String massage) {
//        Alert alert = new WebDriverWait(wd,1).until(ExpectedConditions.alertIsPresent());
//        if (alert != null && alert.getText().contains(massage)) {
//            alert.accept();
//        }
//        return true;
//    }

    public void generateAndSaveTestData() {
        List<String> invalidEmails = generateInvalidEmails(25);
        List<String> invalidPasswords = generateInvalidPasswords(25);

        saveToFile(invalidEmails, "invalid_emails.txt");
        saveToFile(invalidPasswords, "invalid_passwords.txt");
    }
    public List<String> generateInvalidPasswords(int count) {
        List<String> passwords = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            StringBuilder password = new StringBuilder();
            int errorType = random.nextInt(4);
            switch (errorType) {
                case 0:
                    password.append(generateRandomDigits(6, 8));
                    break;
                case 1:
                    password.append(generateRandomString(6, 8));
                    break;
                case 2:
                    password.append(generateRandomString(6, 8)).append(generateRandomDigits(2, 4));
                    break;
                case 3:
                    password.append(generateRandomString(3, 4)).append(generateRandomDigits(1, 2));
                    break;
            }
            passwords.add(password.toString());
        }
        return passwords;
    }
    private String generateRandomDigits(int minLength, int maxLength) {
        int length = random.nextInt(maxLength - minLength + 1) + minLength;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
    public List<String> generateInvalidEmails(int count) {
            List<String> emails = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                int type = random.nextInt(4); // Randomly select one of four types
                String email;
                switch (type) {
                    case 0:
                        email = generateRandomString(5, 10) + "@@" + generateRandomDomain(); // @@ in email
                        break;
                    case 1:
                        email = generateRandomString(5, 10) + generateRandomDomain(); // No @ in email
                        break;
                    case 2:
                        email = generateRandomString(5, 10) + "@"; // @ but no domain
                        break;
                    default:
                        email = "@" + generateRandomDomain(); // Domain but no user part
                        break;
                }
                emails.add(email);
            }
            return emails;
        }
    private String generateRandomString(int minLength, int maxLength) {
        int length = random.nextInt(maxLength - minLength + 1) + minLength;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char) (random.nextInt(26) + 'a'));
        }
        return sb.toString();
    }
    private String generateRandomDomain() {
        return generateRandomString(3, 5) + ".com";
    }
    public void saveToFile(List<String> data, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<String> readFromFile(String filename) {
        List<String> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    public List<String> readInvalidEmailsFromFile(String filename) {
        return readFromFile(filename);
    }
    public List<String> readInvalidPasswordsFromFile(String filename) {
        return readFromFile(filename);
    }
    public String getErrorTxt() {
        return wd.findElement(By.cssSelector("div.error"))
                .getText();
    }
    public void openRegInForm(){
        click("Sign Up");
    }
    public void fillRegInForm(User user){
        type(By.id("name"),user.getFirstName());
        type(By.id("lastName"),user.getLastName());
        type(By.id("email"),user.getEmail());
        type(By.id("password"),user.getPass());
    }
    public void checkPolicy(){
        JavascriptExecutor js = (JavascriptExecutor) wd;
        js.executeScript("document.querySelector('#terms-of-use').click()");
    }
    public void checkPolicyXY(){
      WebElement label = wd.findElement(By.cssSelector("label[for='terms-of-use']"));
        Rectangle re = label.getRect();
      int w = re.getWidth();
      int xOffset = -w/2;
        Actions actions = new Actions(wd);
        actions.moveToElement(label, xOffset,0).click().release().perform();
    Dimension size = wd.manage().window().getSize();
        System.out.println("Wight screen --->"+size.getWidth());
    }
    public void login(User user){
        openLoginRegistrationForm();
        fillLoginRegistrationForm(user);
        submitLogin();
        clickOk();
    }
}
