package com.ozyegin.csc393.car_rental_project.domain;

import com.ozyegin.csc393.car_rental_project.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    private String reservationNumber;
    private LocalDateTime createdDate;
    private LocalDateTime pickupDateTime;
    private LocalDateTime dropoffDateTime;
    private LocalDateTime returnDateTime;
    private ReservationStatus status;

    private Member member;
    private Car car;
    private Location pickupLocation;
    private Location dropoffLocation;


    private List<Extra> extras = new ArrayList<>();
}
