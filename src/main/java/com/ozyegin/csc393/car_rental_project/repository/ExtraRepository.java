package com.ozyegin.csc393.car_rental_project.repository;

import com.ozyegin.csc393.car_rental_project.domain.Extra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtraRepository extends JpaRepository<Extra, Long> {
}