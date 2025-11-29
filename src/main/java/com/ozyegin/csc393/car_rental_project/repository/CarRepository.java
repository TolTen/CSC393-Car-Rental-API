package com.ozyegin.csc393.car_rental_project.repository;

import com.ozyegin.csc393.car_rental_project.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findByBarcode(String barcode);
}