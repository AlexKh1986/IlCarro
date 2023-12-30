package manager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HelperUser extends HelperBase {

    public HelperUser(WebDriver wd) {
        super(wd);
    }

    public void openLoginRegistrationForm() {
        clickButtonByNumber("Log in", 1);
    }

    public void fillLoginRegistrationForm(String Email, String Password) {
        type(By.id("email"),Email);
        type(By.id("password"), Password);
    }

    public void submitLogin() {
        WebElement button = wd.findElement(By.xpath("//button[contains(text(), 'Y’alla!')]"));
        button.click();
    }

    public boolean isLogged() {
        return
                isElementPresent(By.xpath("//*[.=' Logout ']"));
    }

    public void logout() {
        click("Logout");
    }

}
