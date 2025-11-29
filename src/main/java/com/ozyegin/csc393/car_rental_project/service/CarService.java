package com.ozyegin.csc393.car_rental_project.service;

import com.ozyegin.csc393.car_rental_project.domain.Car;
import com.ozyegin.csc393.car_rental_project.domain.Location;
import com.ozyegin.csc393.car_rental_project.dto.CarDTO;
import com.ozyegin.csc393.car_rental_project.repository.CarRepository;
import com.ozyegin.csc393.car_rental_project.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final LocationRepository locationRepository;

    public CarService(CarRepository carRepository, LocationRepository locationRepository) {
        this.carRepository = carRepository;
        this.locationRepository = locationRepository;
    }

    public CarDTO createCar(CarDTO carDTO) {
        Car car = mapToEntity(carDTO);
        car = carRepository.save(car);
        return mapToDTO(car);
    }

    public List<CarDTO> getAllCars() {
        return carRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public CarDTO getCarById(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Car not found with id: " + id));
        return mapToDTO(car);
    }

    @Transactional
    public CarDTO updateCar(Long id, CarDTO carDTO) {
        Car existing = carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Car not found"));


        existing.setBarcode(carDTO.getBarcode());
        existing.setLicensePlate(carDTO.getLicensePlate());
        existing.setBrand(carDTO.getBrand());
        existing.setModel(carDTO.getModel());
        existing.setSeatCount(carDTO.getSeatCount());
        existing.setMileage(carDTO.getMileage());
        existing.setDailyPrice(carDTO.getDailyPrice());
        existing.setCategory(carDTO.getCategory());
        existing.setTransmission(carDTO.getTransmission());
        existing.setStatus(carDTO.getStatus());


        if (carDTO.getCurrentLocationCode() != null) {
            Location loc = locationRepository.findByCode(carDTO.getCurrentLocationCode()).orElseThrow(() -> new IllegalArgumentException("Location code not found"));
            existing.setCurrentLocation(loc);
        }

        Car saved = carRepository.save(existing);
        return mapToDTO(saved);
    }

    public void deleteCarById(Long id) {
        try {
            carRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Cannot delete car! It might be associated with active reservations.");
        }
    }


    private Car mapToEntity(CarDTO dto) {
        Car car = new Car();
        car.setId(dto.getId());
        car.setBarcode(dto.getBarcode());
        car.setLicensePlate(dto.getLicensePlate());
        car.setBrand(dto.getBrand());
        car.setModel(dto.getModel());
        car.setSeatCount(dto.getSeatCount());
        car.setMileage(dto.getMileage());
        car.setDailyPrice(dto.getDailyPrice());
        car.setCategory(dto.getCategory());
        car.setTransmission(dto.getTransmission());
        car.setStatus(dto.getStatus());

        if (dto.getCurrentLocationCode() != null) {
            Location loc = locationRepository.findByCode(dto.getCurrentLocationCode()).orElseThrow(() -> new IllegalArgumentException("Location not found: " + dto.getCurrentLocationCode()));
            car.setCurrentLocation(loc);
        }
        return car;
    }

    private CarDTO mapToDTO(Car entity) {
        CarDTO dto = new CarDTO();
        dto.setId(entity.getId());
        dto.setBarcode(entity.getBarcode());
        dto.setLicensePlate(entity.getLicensePlate());
        dto.setBrand(entity.getBrand());
        dto.setModel(entity.getModel());
        dto.setSeatCount(entity.getSeatCount());
        dto.setMileage(entity.getMileage());
        dto.setDailyPrice(entity.getDailyPrice());
        dto.setCategory(entity.getCategory());
        dto.setTransmission(entity.getTransmission());
        dto.setStatus(entity.getStatus());
        if (entity.getCurrentLocation() != null) {
            dto.setCurrentLocationCode(entity.getCurrentLocation().getCode());
        }
        return dto;
    }
}