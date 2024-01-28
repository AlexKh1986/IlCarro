package tests;

import manager.CarDataGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.*;


public class SearchCarsTests extends TestBase {
    private static final Logger log = Logger.getLogger(SearchCarsTests.class.getName());
    private final CarDataGenerator dataGenerator = new CarDataGenerator();

    static {
        try {
            LogManager.getLogManager().reset();
            log.setLevel(Level.ALL);
            FileHandler fileHandler = new FileHandler("testLogSearching.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            log.addHandler(fileHandler);
        } catch (IOException e) {
            log.log(Level.SEVERE, "File logger not working.", e);
        }
    }

    private void clickWithRetry(By locator) {
        for (int i = 0; i < 3; i++) {
            try {
                app.getDriver().findElement(locator).click();
                return;
            } catch (ElementClickInterceptedException e) {
                // Optionally wait for a short period before retrying
            }
        }
        throw new ElementClickInterceptedException("Failed to click element after " + 3 + " attempts.");

    }

    @Test
    public void searchWithValidData() {
        int numberOfTests = 25;
        log.info("Starting searchWithValidData test with " + numberOfTests + " iterations");
        for (int i = 0; i < numberOfTests; i++) {
            log.info("Iteration " + (i + 1) + " of searchWithValidData test");
            String validLocation = dataGenerator.generateValidLocation();
            String validDateStart = dataGenerator.generateValidDate();
            String validDateEnd = dataGenerator.generateValidEndDate(validDateStart);
            log.info("Generated search parameters - Location: " + validLocation + ", Start Date: " + validDateStart
                    + ", End Date: " + validDateEnd);
            try {
                app.getHelperCar().search(validLocation, validDateStart, validDateEnd);
                app.getHelperCar().submit();
                WebElement element = app.getDriver().findElement(By.id("dates"));
                ((JavascriptExecutor) app.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
                clickWithRetry(By.id("dates"));
            } catch (Exception e) {
                log.severe("Exception encountered: " + e.getMessage());
            }
            app.getDriver().navigate().refresh();
        }
        log.info("Completed searchWithValidData test");
    }

    @Test
    public void searchWithValidLocationAndInvalidDate() {
        int numberOfTests = 25;
        log.info("Starting searchWithValidLocationAndInvalidDate test with " + numberOfTests + " iterations");
        for (int i = 0; i < numberOfTests; i++) {
            log.info("Iteration " + (i + 1) + " of searchWithValidLocationAndInvalidDate test");
            String validLocation = dataGenerator.generateValidLocation();
            String invalidDateStart = dataGenerator.generateInvalidDate();
            String invalidDateEnd = dataGenerator.generateInvalidDate();
            log.info("Generated search parameters - Location: " + validLocation + ", Start Date: " + invalidDateStart
                    + ", End Date: " + invalidDateEnd);
            try {
                app.getHelperCar().search(validLocation, invalidDateStart, invalidDateEnd);
                app.getHelperCar().submit();
                WebElement element = app.getDriver().findElement(By.id("dates"));
                ((JavascriptExecutor) app.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
                clickWithRetry(By.id("dates"));
            } catch (Exception e) {
                log.severe("Exception encountered: " + e.getMessage());
            }
            app.getDriver().navigate().refresh();
        }
        log.info("Completed searchWithValidData test");
    }

    @Test
    public void searchWithInvalidLocationAndValidDates() {
        int numberOfTests = 25;
        log.info("Starting searchWithInvalidLocationAndValidDates test with " + numberOfTests + " iterations");
        for (int i = 0; i < numberOfTests; i++) {
            log.info("Iteration " + (i + 1) + " of searchWithInvalidLocationAndValidDates test");
            String invalidLocation = dataGenerator.generateInvalidLocation();
            String validDateStart = dataGenerator.generateValidDate();
            String validDateEnd = dataGenerator.generateValidEndDate(validDateStart);
            log.info("Generated search parameters - Location: " + invalidLocation + ", Start Date: "
                    + validDateStart + ", End Date: " + validDateEnd);

            try {
                app.getHelperCar().search(invalidLocation, validDateStart, validDateEnd);
                app.getHelperCar().submit();
                WebElement element = app.getDriver().findElement(By.id("dates"));
                ((JavascriptExecutor) app.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
                clickWithRetry(By.id("dates"));
            } catch (Exception e) {
                log.severe("Exception encountered: " + e.getMessage());
            }
            app.getDriver().navigate().refresh();
        }
        log.info("Completed searchWithValidData test");
    }

    @Test
    public void searchWithValidYears(){

    }
    @AfterMethod
    public void postCondition() {
        app.getDriver().quit();
    }
}


