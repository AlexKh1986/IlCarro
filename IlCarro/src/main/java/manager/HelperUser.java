package manager;

import models.User;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelperUser extends HelperBase {
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
        // Генерация данных
        List<String> invalidEmails = generateInvalidEmails(100);
        List<String> invalidPasswords = generateInvalidPasswords(100);

        // Сохранение данных в файлы
        saveToFile(invalidEmails, "invalid_emails.txt");
        saveToFile(invalidPasswords, "invalid_passwords.txt");
    }
    public List<String> generateInvalidPasswords(int count) {
        List<String> passwords = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            StringBuilder password = new StringBuilder();
            int errorType = random.nextInt(4);
            switch (errorType) {
                case 0: // Только цифры
                    password.append(generateRandomDigits(6, 8));
                    break;
                case 1: // Только буквы
                    password.append(generateRandomString(6, 8));
                    break;
                case 2: // Без специальных символов
                    password.append(generateRandomString(6, 8)).append(generateRandomDigits(2, 4));
                    break;
                case 3: // Слишком короткий
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
            sb.append(random.nextInt(10)); // Генерация случайных цифр
        }
        return sb.toString();
    }

    // Генерация невалидных электронных адресов
    public List<String> generateInvalidEmails(int count) {
        List<String> emails = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            StringBuilder email = new StringBuilder();
            email.append(generateRandomString(5, 10));

            int errorType = random.nextInt(5);
            switch (errorType) {
                case 0:
                    email.append(generateRandomDomain());
                    break;
                case 1:
                    email.append("@@").append(generateRandomDomain());
                    break;
                case 2:
                    email.append("@").append(generateRandomString(3, 5)).append(".co");
                    break;
                case 3:
                    email.append("@").append(generateRandomString(3, 5));
                    break;
                case 4:
                    email.append("@").append(generateRandomDomain());
                    break;
            }
            emails.add(email.toString());
        }
        return emails;
    }

    // Вспомогательные методы
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

    // Сохранение данных в файл
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

    // Чтение данных из файла
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
}
