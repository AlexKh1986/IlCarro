package manager;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class ApplicationManager {
    WebDriver wd;
    @Getter
    HelperUser helperUser;
    @Getter
    HelperCar helperCar;

    public void init() {
        System.setProperty("webdriver.chrome.driver", "/Users/alexkhenkin/Tools/chromedriver/chromedriver");
        wd = new ChromeDriver();
        wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wd.manage().window().maximize();
        wd.navigate().to("https://ilcarro.web.app/search");
        helperUser = new HelperUser(wd);
        helperCar = new HelperCar(wd);

    }

    public void stop() {
        wd.quit();
    }

    public WebDriver getDriver() {
        return wd;
    }
}
