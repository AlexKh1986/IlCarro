package models;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.openqa.selenium.WebDriver;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

@Setter
@Getter
@ToString
@Builder
public class Car {

    private String location;
    private String manufacture;
    private String model;
    private String year;
    private String carClass;
    private String carRegNumber;
    private String fuel;
    private String about;
    private int seats;
    private double price;

    public Car(String location, String manufacture, String model, String year, String carClass, String carRegNumber, String fuel, String about, int seats, double price) {
        this.location = location;
        this.manufacture = manufacture;
        this.model = model;
        this.year = year;
        this.carClass = carClass;
        this.carRegNumber = carRegNumber;
        this.fuel = fuel;
        this.about = about;
        this.seats = seats;
        this.price = price;
    }

    public Car() {

    }
}