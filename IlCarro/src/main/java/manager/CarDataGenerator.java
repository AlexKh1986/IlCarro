package manager;

import models.Car;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CarDataGenerator {
    String[] locations = {"Tel Aviv", "Jerusalem", "Haifa"};
    String[] manufactures = {"Toyota", "Honda", "Ford", "Nissan", "Volkswagen",
            "Hyundai", "Chevrolet", "Kia", "Mercedes-Benz", "BMW"};
    String[] models = {"Corolla", "Civic", "F-150", "Altima", "Golf",
            "Elantra", "Silverado", "Optima", "C-Class", "3 Series"};
    String[] years = {"2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024"};
    String[] fuels = {"Petrol", "Diesel", "Hybrid", "Electric", "Gas"};
    int[] seats = {2, 4, 5, 7};
    double[] prices = {100, 230, 1000, 350, 400, 450, 500};



    public void generateCarsFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < 25; i++) {
                Car car = generateCar();
                writer.write(carToString(car));
                writer.newLine();
            }
        }
    }

    private Car generateCar() {
        Random random = new Random();
        return Car.builder()
                .location(locations[random.nextInt(locations.length)])
                .manufacture(manufactures[random.nextInt(manufactures.length)])
                .model(models[random.nextInt(models.length)])
                .year(years[random.nextInt(years.length)])
                .carClass(generateCarClass())
                .carRegNumber("REG-" + random.nextInt(9999))
                .fuel(fuels[random.nextInt(fuels.length)])
                .about("Very nice car")
                .seats(seats[random.nextInt(seats.length)])
                .price(prices[random.nextInt(prices.length)])
                .build();
    }
    private String generateCarClass() {
        String[] carClasses = {"Econom", "Lux"};
        return carClasses[new Random().nextInt(carClasses.length)];
    }


    private String carToString(Car car) {
        return "location=" + car.getLocation() +
                ", manufacture=" + car.getManufacture() +
                ", model=" + car.getModel() +
                ", year=" + car.getYear() +
                ", carClass=" + car.getCarClass() +
                ", carRegNumber=" + car.getCarRegNumber() +
                ", fuel=" + car.getFuel() +
                ", about=" + car.getAbout() +
                ", seats=" + car.getSeats() +
                ", price=" + car.getPrice();
    }






}

