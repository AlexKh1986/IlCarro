package manager;

import models.Car;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CarDataGenerator {
    Random r = new Random();
    String[] locations = {"Tel Aviv", "Jerusalem", "Haifa"};
    String[] manufactures = {"Toyota", "Honda", "Ford", "Nissan", "Volkswagen",
            "Hyundai", "Chevrolet", "Kia", "Mercedes-Benz", "BMW"};
    String[] models = {"Corolla", "Civic", "F-150", "Altima", "Golf",
            "Elantra", "Silverado", "Optima", "C-Class", "3 Series"};
    String[] years = {"2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024"};
    String[] fuels = {"Petrol", "Diesel", "Hybrid", "Electric", "Gas"};
    int[] seats = {2, 4, 5, 7};
    double[] prices = {100, 230, 1000, 350, 400, 450, 500};
    private static final List<String> validLocations = Arrays.asList("Tel Aviv", "Haifa", "Jerusalem");



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

        return Car.builder()
                .location(locations[r.nextInt(locations.length)])
                .manufacture(manufactures[r.nextInt(manufactures.length)])
                .model(models[r.nextInt(models.length)])
                .year(years[r.nextInt(years.length)])
                .carClass(generateCarClass())
                .carRegNumber("REG-" + r.nextInt(9999))
                .fuel(fuels[r.nextInt(fuels.length)])
                .about("Very nice car")
                .seats(seats[r.nextInt(seats.length)])
                .price(prices[r.nextInt(prices.length)])
                .build();
    }

    private String generateCarClass() {
        String[] carClasses = {"Econom", "Lux", "Premium"};
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

    private Car generateInvalidCar() {
        String defaultLocation = " ";
        String defaultManufacture = " ";
        String defaultModel = " ";
        String defaultYear = "200";
        String defaultCarClass = "Econom";
        String defaultCarRegNumber = "0000";
        String defaultFuel = " ";
        String defaultAbout = "Default";
        int defaultSeats = 0;
        double defaultPrice = 0.0;
        Car.CarBuilder carBuilder = Car.builder()
                .location(r.nextBoolean() ? locations[r.nextInt(locations.length)] : defaultLocation)
                .manufacture(r.nextBoolean() ? manufactures[r.nextInt(manufactures.length)] : defaultManufacture)
                .model(r.nextBoolean() ? models[r.nextInt(models.length)] : defaultModel)
                .year(r.nextBoolean() ? years[r.nextInt(years.length)] : defaultYear)
                .carClass(r.nextBoolean() ? generateCarClass() : defaultCarClass)
                .carRegNumber(r.nextBoolean() ? "REG-" + r.nextInt(9999) : defaultCarRegNumber)
                .fuel(r.nextBoolean() ? fuels[r.nextInt(fuels.length)] : defaultFuel)
                .about(r.nextBoolean() ? "Very nice car" : defaultAbout)
                .seats(r.nextBoolean() ? seats[r.nextInt(seats.length)] : defaultSeats)
                .price(r.nextBoolean() ? prices[r.nextInt(prices.length)] : defaultPrice);

        return carBuilder.build();
    }

    public void generateInvalidCarsFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < 25; i++) {
                Car car = generateInvalidCar();
                writer.write(carToString(car));
                writer.newLine();
            }
        }
    }

    //    public  String generateValidDate() {
//        LocalDate date = LocalDate.now().plusDays(new Random().nextInt(365)); // Добавляем случайное количество дней (до одного года вперед)
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//        return date.format(formatter);
//    }
//    public  String generateInvalidDate() {
//        LocalDate date = LocalDate.now().minusDays(new Random().nextInt(365) + 1); // Убираем случайное количество дней (на год назад)
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//        return date.format(formatter);
//    }
//    private static final List<String> validLocations = Arrays.asList(
//            "Tel Aviv",
//            "Haifa",
//            "Jerusalem"
//    );
//
//    public  String generateValidLocation() {
//        return validLocations.get(new Random().nextInt(validLocations.size()));
//    }
//    public  String generateInvalidLocation() {
//        int leftLimit = 97;
//        int rightLimit = 122;
//        int targetStringLength = 10;
//        Random random = new Random();
//        StringBuilder buffer = new StringBuilder(targetStringLength);
//        for (int i = 0; i < targetStringLength; i++) {
//            int randomLimitedInt = leftLimit + (int)
//                    (random.nextFloat() * (rightLimit - leftLimit + 1));
//            buffer.append((char) randomLimitedInt);
//        }
//        return buffer.toString();
//    }
    public String generateValidDate() {
        LocalDate date = LocalDate.now().plusDays(new Random().nextInt(365));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return date.format(formatter);
    }
    public String generateValidEndDate(String startDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = startDate.plusDays(1 + new Random().nextInt(365)); // Гарантируем, что дата окончания позже даты начала
        return endDate.format(formatter);
    }

    public String generateValidLocation() {
        return validLocations.get(new Random().nextInt(validLocations.size()));
    }

    public String generateInvalidLocation() {
        int leftLimit = 97; // ASCII code for 'a'
        int rightLimit = 122; // ASCII code for 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + random.nextInt(rightLimit - leftLimit + 1);
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
    public String generateInvalidDate() {
        LocalDate date = LocalDate.now().minusDays(new Random().nextInt(365) + 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return date.format(formatter);
    }
}

