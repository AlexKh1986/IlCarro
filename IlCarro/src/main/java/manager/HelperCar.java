package manager;

import models.Car;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HelperCar extends HelperBase{
    public HelperCar(WebDriver wd){
        super(wd);
    }
    public void openCarForm(){
        click("Let the car work");
    }
    public void fillCarForm(Car car){
        typeLocation(car.getLocation());
    }
    private void typeLocation(String location){
        type(By.id("pickUpPlace"),location);
        click(By.cssSelector("div.pac-item"));
    }
}
