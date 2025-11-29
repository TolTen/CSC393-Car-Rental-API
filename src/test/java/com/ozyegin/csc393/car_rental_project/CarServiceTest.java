package com.ozyegin.csc393.car_rental_project;

import com.ozyegin.csc393.car_rental_project.domain.Car;
import com.ozyegin.csc393.car_rental_project.domain.Location;
import com.ozyegin.csc393.car_rental_project.dto.CarDTO;
import com.ozyegin.csc393.car_rental_project.enums.CarCategory;
import com.ozyegin.csc393.car_rental_project.enums.CarStatus;
import com.ozyegin.csc393.car_rental_project.enums.TransmissionType;
import com.ozyegin.csc393.car_rental_project.repository.CarRepository;
import com.ozyegin.csc393.car_rental_project.repository.LocationRepository;
import com.ozyegin.csc393.car_rental_project.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CarServiceTest {

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private LocationRepository locationRepository;


    private Location testLocation;

    @BeforeEach
    void setUp() {


        testLocation = new Location();
        testLocation.setCode("TEST_LOC");
        testLocation.setName("Test Location");
        locationRepository.save(testLocation);
    }

    @Test
    void shouldCreateCar() {

        CarDTO inputDto = new CarDTO();
        inputDto.setBarcode("NEW_CAR_1");
        inputDto.setLicensePlate("34 AB 1234");
        inputDto.setBrand("Tesla");
        inputDto.setModel("Model Y");
        inputDto.setSeatCount(5);
        inputDto.setMileage(0);
        inputDto.setDailyPrice(BigDecimal.valueOf(2000));
        inputDto.setCategory(CarCategory.LUXURY);
        inputDto.setTransmission(TransmissionType.AUTOMATIC);
        inputDto.setStatus(CarStatus.AVAILABLE);
        inputDto.setCurrentLocationCode(testLocation.getCode());


        CarDTO result = carService.createCar(inputDto);


        assertNotNull(result.getId());
        assertEquals("NEW_CAR_1", result.getBarcode());


        assertTrue(carRepository.findByBarcode("NEW_CAR_1").isPresent());
    }

    @Test
    void shouldGetCarById() {

        Car car = new Car();
        car.setBarcode("FIND_ME");
        car.setBrand("Ford");
        car.setModel("Focus");
        car.setSeatCount(5);
        car.setMileage(100);
        car.setDailyPrice(BigDecimal.valueOf(1000));
        car.setCategory(CarCategory.MIDSIZE);
        car.setTransmission(TransmissionType.MANUAL);
        car.setStatus(CarStatus.AVAILABLE);
        carRepository.save(car);

        Long savedId = car.getId();


        CarDTO result = carService.getCarById(savedId);


        assertNotNull(result);
        assertEquals("FIND_ME", result.getBarcode());
        assertEquals("Ford", result.getBrand());
    }

    @Test
    void shouldReturnAllCars() {

        long initialCount = carRepository.count();

        Car car1 = new Car();
        car1.setBarcode("LIST_1");
        car1.setSeatCount(5);
        car1.setMileage(100);
        car1.setDailyPrice(BigDecimal.TEN);
        car1.setCategory(CarCategory.SUV);
        car1.setTransmission(TransmissionType.AUTOMATIC);
        car1.setStatus(CarStatus.AVAILABLE);
        carRepository.save(car1);

        Car car2 = new Car();
        car2.setBarcode("LIST_2");
        car2.setSeatCount(5);
        car2.setMileage(100);
        car2.setDailyPrice(BigDecimal.TEN);
        car2.setCategory(CarCategory.SUV);
        car2.setTransmission(TransmissionType.AUTOMATIC);
        car2.setStatus(CarStatus.AVAILABLE);
        carRepository.save(car2);

        List<CarDTO> result = carService.getAllCars();

        assertEquals(initialCount + 2, result.size());
    }

    @Test
    void shouldUpdateCar() {

        Car car = new Car();
        car.setBarcode("TO_UPDATE");
        car.setBrand("OldBrand");
        car.setSeatCount(5);
        car.setMileage(100);
        car.setDailyPrice(BigDecimal.TEN);
        car.setCategory(CarCategory.SUV);
        car.setTransmission(TransmissionType.AUTOMATIC);
        car.setStatus(CarStatus.AVAILABLE);
        carRepository.save(car);

        Long carId = car.getId();


        CarDTO updateInfo = new CarDTO();
        updateInfo.setBarcode("TO_UPDATE");
        updateInfo.setBrand("NewBrand");
        updateInfo.setStatus(CarStatus.BEING_SERVICED);
        updateInfo.setModel("SomeModel");
        updateInfo.setSeatCount(5);
        updateInfo.setMileage(100);
        updateInfo.setDailyPrice(BigDecimal.valueOf(1500));
        updateInfo.setCategory(CarCategory.SUV);
        updateInfo.setTransmission(TransmissionType.AUTOMATIC);

        carService.updateCar(carId, updateInfo);

        Car updatedCar = carRepository.findById(carId).orElseThrow();
        assertEquals("NewBrand", updatedCar.getBrand());
        assertEquals(CarStatus.BEING_SERVICED, updatedCar.getStatus());
    }

    @Test
    void shouldDeleteCar() {

        Car car = new Car();
        car.setBarcode("TO_DELETE");
        car.setSeatCount(5);
        car.setMileage(100);
        car.setDailyPrice(BigDecimal.TEN);
        car.setCategory(CarCategory.SUV);
        car.setTransmission(TransmissionType.AUTOMATIC);
        car.setStatus(CarStatus.AVAILABLE);
        carRepository.save(car);

        Long carId = car.getId();

        carService.deleteCarById(carId);

        assertFalse(carRepository.findById(carId).isPresent());
    }
}