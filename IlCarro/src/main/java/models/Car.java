package models;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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


}
