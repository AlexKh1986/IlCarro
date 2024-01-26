package tests;

import manager.CarDataGenerator;
import models.Car;
import models.User;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;


public class AddNewCarTests extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(AddNewCarTests.class);
    private final CarDataGenerator generator = new CarDataGenerator();

    public void testDefault(Car car) {
        app.getHelperCar().openCarForm();
        app.getHelperCar().fillCarForm(car);
        app.getHelperCar().submit();
    }

    public void testDefaultNegativeVar(Car car) {
        app.getHelperCar().openCarForm();
        app.getHelperCar().fillCarForm(car);
        try {
            app.getHelperCar().submit();
        } catch (Exception e) {
            logger.error("Submit failed for car: " + car, e);
        }
    }


    @BeforeClass
    public void preCondition() {
        logger.info("Starting pre-condition for AddNewCarTests");
        if (!app.getHelperUser().isLogged()) {
            app.getHelperUser().login(new User().withEmail("alexkhenkin1986@gmail.com").withPass("Po 12 34 !0 9"));
            logger.info("User logged in");
        } else {
            logger.error("App or HelperUser is not initialized");
        }

        try {
            generator.generateCarsFile("cars.txt");
            logger.info("Cars file generated successfully");
        } catch (IOException e) {
            logger.error("Failed to generate cars file", e);
            throw new RuntimeException(e);
        }

    }

    @BeforeMethod
    public void setUpForInvalidCars() {
        try {
            generator.generateInvalidCarsFile("invalid_cars.txt");
            logger.info("Invalid cars file generated successfully");
        } catch (IOException e) {
            logger.error("Error generating invalid cars file", e);
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addInvalidCarTest() {
        List<Car> cars = app.getHelperCar().readCarsFromFile("invalid_cars.txt");
        for (Car car : cars) {
            try {
                testDefaultNegativeVar(car);
                boolean isAddedSuccessfully = app.getHelperCar().getMessage().contains("added successful");
                Assert.assertFalse(isAddedSuccessfully, "Invalid car should not be added");
                logger.info("Tested invalid car addition, which failed as expected: " + car);
            } catch (NoSuchElementException e) {
                logger.error("Exception occurred for car: " + car, e);
                logger.info("Expected exception for invalid car: " + e.getMessage());
                app.getHelperCar().getScreen("src/test/screenshots");
                //    app.getDriver().navigate().refresh();
            } finally {
//                //continue;
//                logger.info("Returning to refresh");
//                app.getDriver().navigate().refresh();
//            }
//            app.getHelperCar().returnToHome();
                logger.info("Refreshing and returning to home page");
                app.getDriver().navigate().refresh();
             //   app.getHelperCar().returnToHome();
            }
        }
    }

    @Test
    public void addNewCarSuccessAll() {
        List<Car> cars = app.getHelperCar().readCarsFromFile("cars.txt");
        for (Car car : cars) {
            testDefault(car);
            boolean isAddedSuccessfully = app.getHelperCar().getMessage().contains("added successful");
            Assert.assertTrue(isAddedSuccessfully, "Car was not added successfully");
            logger.info("Car added successfully: " + car);
            app.getHelperCar().returnToHome();
        }
    }

    @AfterMethod
    public void postCondition() {
        if (app.getHelperCar() != null) {
            app.getHelperUser().logout();
            logger.info("Returned to home after the test");
        } else {
            logger.error("HelperCar is not initialized in postCondition");
        }
    }


}
