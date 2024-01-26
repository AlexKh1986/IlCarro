package manager;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
   // WebDriver wd;
    @Getter
    HelperUser helperUser;
    @Getter
    HelperCar helperCar;
    EventFiringWebDriver wd;
    Logger logger= LoggerFactory.getLogger(ApplicationManager.class);



    public void init() {
        System.setProperty("webdriver.chrome.driver", "/Users/alexkhenkin/Tools/chromedriver/chromedriver");
        wd = new EventFiringWebDriver(new ChromeDriver());
        wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wd.manage().window().maximize();
        wd.navigate().to("https://ilcarro.web.app/search");
        helperUser = new HelperUser(wd);
        helperCar = new HelperCar(wd);
        logger.info("All tests run in Chrome Browser");
        logger.info("The link --> "+wd.getCurrentUrl());
        wd.register(new ListenerWD());

    }

    public void stop() {
        wd.quit();
    }

    public WebDriver getDriver() {
        return wd;
    }
}
