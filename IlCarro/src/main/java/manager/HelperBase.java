package manager;

//import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.nio.file.Files;


public class HelperBase {
    Logger logger = LoggerFactory.getLogger(HelperBase.class);
    WebDriver wd;

    public HelperBase(WebDriver wd) {
        this.wd = wd;
    }

    //    public void type(String fieldLabel, String text) {
//        String xpathQuery = "//*[translate(@placeholder, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = '%s' or translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = '%s']";
//        List<WebElement> elements = wd.findElements(By.xpath(xpathQuery));
//        if (!elements.isEmpty()) {
//            WebElement el = elements.get(0);
//            el.click();
//            el.clear();
//            if (text != null) {
//                el.sendKeys(text);
//            }
//        } else {
//            System.out.println(fieldLabel + " field not found.");
//        }
//    }
    public void type(By locator, String text) {
        WebElement element = wd.findElement(locator);
        if (element != null) {
            element.click();
            element.clear();
            if (text != null) {
                element.sendKeys(text);
            }
        }

    }

    public void click(String buttonLabel) {
        String lowerCaseLabel = buttonLabel.toLowerCase();
        String xpathQuery = String.format("//*[translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = '%s']",
                lowerCaseLabel);
        List<WebElement> buttons = wd.findElements(By.xpath(xpathQuery));
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
        } else {
            System.out.println(buttonLabel + " button not found.");
        }
    }

    public void click(By locator) {
        wd.findElement(locator).click();
    }

    public void clickButtonByNumber(String buttonLabel, int number) {
        String xpathQuery = String.format("(//*[contains(text(), '%s')])[%d]", buttonLabel, number);
        WebElement button = wd.findElement(By.xpath(xpathQuery));
        if (button != null) {
            button.click();
        } else {
            System.out.println("Button with specified order not found.");
        }
    }

    //    public boolean isElementPresent(String elementText) {
//        By locator = By.xpath(String.format("//*[contains(text(), '%s')]", elementText));
//        List<WebElement> elList = wd.findElements(locator);
//        return !elList.isEmpty();
//    }
    public boolean isElementPresent(By locator) {
        List<WebElement> list = wd.findElements(locator);
        return !list.isEmpty();
    }

    //    public boolean isElementPresentByLocator(By locator){
//        List<WebElement>list = wd.findElements(locator);
//        return list.size()>0;
//    }
    public String getMessage() {
        pause(5000);
        return wd.findElement(By.cssSelector(".dialog-container>h2"))
                .getText();
    }

    public void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void submit() {
        wd.findElement(By.xpath("//button[@type='submit']"))
                .click();
    }

    public void clickOk() {
        click("ok");
    }

    public void getScreen(String link) {
        File directory = new File(link);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String filename = "screenshot-" + System.currentTimeMillis() + ".png";
        File screenshotFile = new File(directory, filename);
        TakesScreenshot takesScreenshot = (TakesScreenshot) wd;
        File tmp = takesScreenshot.getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(tmp.toPath(), screenshotFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            logger.info("Screenshot saved to: " + screenshotFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Error while saving screenshot: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
//    public void getScreen(String link) {
//
//        TakesScreenshot takesScreenshot=(TakesScreenshot) wd;
//        File tmp =takesScreenshot.getScreenshotAs(OutputType.FILE);
//        try {
//            Files.copy(tmp,new File(link));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
public void clearTextBox(By locator){
    WebElement el = wd.findElement(locator);
    String os = System.getProperty("os.name");
    System.out.println(os);
    if(os.startsWith("Win")) {
        el.sendKeys(Keys.CONTROL, "a");
    }else {
        el.sendKeys(Keys.COMMAND, "a");
    }
    el.sendKeys(Keys.DELETE);

}
}


