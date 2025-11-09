package com.ozyegin.csc393.car_rental_project.domain;

import com.ozyegin.csc393.car_rental_project.enums.CarCategory;
import com.ozyegin.csc393.car_rental_project.enums.CarStatus;
import com.ozyegin.csc393.car_rental_project.enums.TransmissionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    private String barcode;
    private String licensePlate;
    private String brand;
    private String model;
    private int seatCount;
    private long mileage;
    private BigDecimal dailyPrice;
    private CarCategory category;
    private TransmissionType transmission;
    private CarStatus status;

    private Location currentLocation;

    

    
}