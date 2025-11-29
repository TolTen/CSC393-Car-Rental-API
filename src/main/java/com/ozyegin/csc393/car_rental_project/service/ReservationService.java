package com.ozyegin.csc393.car_rental_project.service;

import com.ozyegin.csc393.car_rental_project.domain.*;
import com.ozyegin.csc393.car_rental_project.dto.ReservationDTO;
import com.ozyegin.csc393.car_rental_project.enums.CarStatus;
import com.ozyegin.csc393.car_rental_project.enums.ReservationStatus;
import com.ozyegin.csc393.car_rental_project.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;
    private final Random random = new Random();

    public ReservationService(ReservationRepository reservationRepository, CarRepository carRepository, MemberRepository memberRepository, LocationRepository locationRepository) {
        this.reservationRepository = reservationRepository;
        this.carRepository = carRepository;
        this.memberRepository = memberRepository;
        this.locationRepository = locationRepository;
    }

    @Transactional
    public ReservationDTO createReservation(ReservationDTO dto) {
        Car car = carRepository.findByBarcode(dto.getCarBarcode()).orElseThrow(() -> new IllegalArgumentException("Car not found with barcode: " + dto.getCarBarcode()));

        if (car.getStatus() != CarStatus.AVAILABLE) {
            throw new RuntimeException("Car is NOT available! Current status: " + car.getStatus());
        }

        Member member = memberRepository.findByDrivingLicenseNumber(dto.getMemberDrivingLicenseNumber()).orElseThrow(() -> new IllegalArgumentException("Member not found!"));

        Location pickupLoc = locationRepository.findByCode(dto.getPickupLocationCode()).orElseThrow(() -> new IllegalArgumentException("Pickup Location not found!"));
        Location dropoffLoc = locationRepository.findByCode(dto.getDropoffLocationCode()).orElseThrow(() -> new IllegalArgumentException("Dropoff Location not found!"));

        Reservation reservation = new Reservation();
        reservation.setCar(car);
        reservation.setMember(member);
        reservation.setPickupLocation(pickupLoc);
        reservation.setDropoffLocation(dropoffLoc);
        reservation.setPickupDateTime(dto.getPickupDateTime());
        reservation.setDropoffDateTime(dto.getDropoffDateTime());

        reservation.setReservationNumber(generateReservationNumber());
        reservation.setCreatedDate(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.ACTIVE);

        Reservation saved = reservationRepository.save(reservation);
        return mapToDTO(saved);
    }

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }


    private ReservationDTO mapToDTO(Reservation entity) {
        ReservationDTO dto = new ReservationDTO();
        dto.setId(entity.getId());
        dto.setReservationNumber(entity.getReservationNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setStatus(entity.getStatus());
        dto.setPickupDateTime(entity.getPickupDateTime());
        dto.setDropoffDateTime(entity.getDropoffDateTime());

        if(entity.getCar() != null) dto.setCarBarcode(entity.getCar().getBarcode());
        if(entity.getMember() != null) dto.setMemberDrivingLicenseNumber(entity.getMember().getDrivingLicenseNumber());
        if(entity.getPickupLocation() != null) dto.setPickupLocationCode(entity.getPickupLocation().getCode());
        if(entity.getDropoffLocation() != null) dto.setDropoffLocationCode(entity.getDropoffLocation().getCode());

        return dto;
    }

    private String generateReservationNumber() {
        while (true) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                sb.append(random.nextInt(10));
            }
            String candidate = sb.toString();
            if (reservationRepository.findByReservationNumber(candidate).isEmpty()) {
                return candidate;
            }
        }
    }

    public void deleteReservationById(Long id) {
        reservationRepository.deleteById(id);
    }
}