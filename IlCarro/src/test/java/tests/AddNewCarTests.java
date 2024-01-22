package tests;

import manager.CarDataGenerator;
import models.Car;
import models.User;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;


public class AddNewCarTests extends TestBase {
    CarDataGenerator generator = new CarDataGenerator();

    public void testDefault(Car car) {
        app.getHelperCar().openCarForm();
        app.getHelperCar().fillCarForm(car);
        app.getHelperCar().submit();
    }

    @BeforeClass
    public void preCondition() {
        if (app != null && !app.getHelperUser().isLogged()) {
            app.getHelperUser().login(new User().withEmail("alexkhenkin1986@gmail.com").withPass("Po 12 34 !0 9"));
        } else {
            System.out.println("App or HelperUser is not initialized");
        }
        try {
            generator.generateCarsFile("cars.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addNewCarSuccessAll()  {
        List<Car> cars = app.getHelperCar().readCarsFromFile("cars.txt");
        for (Car car : cars) {
            if (app != null && app.getHelperCar() != null) {
                testDefault(car);
                boolean isAddedSuccessfully = app.getHelperCar().getMessage().contains("added successful");
                if (isAddedSuccessfully) {
                    Assert.assertTrue(true, "Car was not added successfully");
                    Assert.assertEquals(app.getHelperCar().getMessage(),
                            car.getManufacture() + " " + car.getModel() + " added successful");
                } else {
                    System.out.println("Failed to add car: " + car);
                }
            } else {
                System.out.println("App or HelperCar is not initialized");
            }
            app.getHelperCar().returnToHome();
        }
    }


    @AfterMethod
    public void postCondition() {
        if (app != null && app.getHelperCar() != null) {
            app.getHelperUser().logout();
        } else {
            System.out.println("App or HelperCar is not initialized in postCondition");
        }
    }


}
