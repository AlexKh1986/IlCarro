package manager;

import models.Car;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelperCar extends HelperBase {
    public HelperCar(WebDriver wd) {
        super(wd);
    }

    public void openCarForm() {
        pause(500);
        click(By.xpath("//a[text()=' Let the car work ']"));
    }

    public void fillCarForm(Car car) {
        typeLocation(car.getLocation());
        type(By.id("make"),car.getManufacture());
        type(By.id("model"), car.getModel());
        type(By.id("year"),car.getYear());
        select(By.id("fuel"), car.getFuel());
        type(By.id("seats"),String.valueOf(car.getSeats()));
        type(By.id("class"),car.getCarClass());
        type(By.id("serialNumber"),car.getCarRegNumber());
        //type(By.id("price"),String.valueOf(car.getPrice())) ;
        type(By.id("price"),car.getPrice()+"");
        type(By.id("about"), car.getAbout());
    }

    private void select(By locator, String option) {
        Select select = new Select(wd.findElement(locator));
        select.selectByValue(option);
        //Gas
//        select.selectByIndex(5);
//        select.selectByValue("Gas");
//        select.selectByVisibleText(" Gas ");

    }


    private void typeLocation(String location) {
        type(By.id("pickUpPlace"),location);
        click(By.cssSelector("div.pac-item"));
    }

    public void returnToHome() {
        click(By.xpath("//button[text()='Search cars']"));
    }

    public void attachPhoto(String link) {
        WebElement element = wd.findElement(By.id("photos"));
        element.sendKeys(link);
    }

    private Car parseCar(String line) {
        try {
            String[] parts = line.split(", ");
            Car car = new Car();
            for (String part : parts) {
                String[] keyValue = part.split("=");
                if (keyValue.length < 2) continue;

                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                switch (key) {
                    case "location":
                        car.setLocation(value);
                        break;
                    case "manufacture":
                        car.setManufacture(value);
                        break;
                    case "model":
                        car.setModel(value);
                        break;
                    case "year":
                        car.setYear(value);
                        break;
                    case "carClass":
                        car.setCarClass(value);
                        break;
                    case "carRegNumber":
                        car.setCarRegNumber(value);
                        break;
                    case "fuel":
                        car.setFuel(value);
                        break;
                    case "about":
                        car.setAbout(value);
                        break;
                    case "seats":
                        car.setSeats(Integer.parseInt(value));
                        break;
                    case "price":
                        car.setPrice(Double.parseDouble(value));
                        break;
                }
            }
            return car;
        } catch (NumberFormatException e) {
            System.err.println("Parsing error for number in line: " + line + " - " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("General parsing error in line: " + line + " - " + e.getMessage());
            return null;
        }
    }



    public List<Car> readCarsFromFile(String filename) {
        List<Car> cars = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Car car = parseCar(line);
                    if (car != null) {
                        cars.add(car);
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println("Skipping invalid car data: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename + " - " + e.getMessage());
        }
        return cars;
    }



}

