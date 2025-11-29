package com.ozyegin.csc393.car_rental_project;

import com.ozyegin.csc393.car_rental_project.domain.*;
import com.ozyegin.csc393.car_rental_project.dto.ReservationDTO;
import com.ozyegin.csc393.car_rental_project.enums.CarCategory;
import com.ozyegin.csc393.car_rental_project.enums.CarStatus;
import com.ozyegin.csc393.car_rental_project.enums.ReservationStatus;
import com.ozyegin.csc393.car_rental_project.enums.TransmissionType;
import com.ozyegin.csc393.car_rental_project.repository.*;
import com.ozyegin.csc393.car_rental_project.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private LocationRepository locationRepository;

    @Test
    void shouldCreateReservation() {

        Location loc = new Location();
        loc.setCode("RES_LOC");
        loc.setName("Reservation Loc");
        locationRepository.save(loc);

        Car car = new Car();
        car.setBarcode("RES_CAR");
        car.setLicensePlate("34 RES 34");
        car.setStatus(CarStatus.AVAILABLE);
        car.setDailyPrice(BigDecimal.valueOf(1000));
        car.setSeatCount(5);
        car.setMileage(100);
        car.setBrand("Fiat");
        car.setModel("Egea");
        car.setCategory(CarCategory.MIDSIZE);
        car.setTransmission(TransmissionType.MANUAL);
        car.setCurrentLocation(loc);
        carRepository.save(car);

        Member member = new Member();
        member.setName("Reserver Member");
        member.setDrivingLicenseNumber("RES_LICENSE");
        memberRepository.save(member);


        ReservationDTO dto = new ReservationDTO();
        dto.setCarBarcode("RES_CAR");
        dto.setMemberDrivingLicenseNumber("RES_LICENSE");
        dto.setPickupLocationCode("RES_LOC");
        dto.setDropoffLocationCode("RES_LOC");
        dto.setPickupDateTime(LocalDateTime.now().plusDays(1));
        dto.setDropoffDateTime(LocalDateTime.now().plusDays(3));


        ReservationDTO result = reservationService.createReservation(dto);


        assertNotNull(result.getId());
        assertNotNull(result.getReservationNumber());
        assertEquals(ReservationStatus.ACTIVE, result.getStatus());
        assertEquals("RES_CAR", result.getCarBarcode());
    }

    @Test
    void shouldListAllReservations() {

        Location loc = new Location();
        loc.setCode("LIST_LOC");
        loc.setName("List Location");
        locationRepository.save(loc);

        Car car = new Car();
        car.setBarcode("LIST_CAR");
        car.setLicensePlate("34 LIST 34");
        car.setStatus(CarStatus.AVAILABLE);
        car.setCategory(CarCategory.SUV);
        car.setTransmission(TransmissionType.AUTOMATIC);
        car.setDailyPrice(BigDecimal.TEN);
        car.setCurrentLocation(loc);
        carRepository.save(car);

        Member member = new Member();
        member.setName("List User");
        member.setDrivingLicenseNumber("LIST_LICENSE");
        memberRepository.save(member);

        ReservationDTO dto = new ReservationDTO();
        dto.setCarBarcode("LIST_CAR");
        dto.setMemberDrivingLicenseNumber("LIST_LICENSE");
        dto.setPickupLocationCode("LIST_LOC");
        dto.setDropoffLocationCode("LIST_LOC");
        dto.setPickupDateTime(LocalDateTime.now().plusDays(1));
        dto.setDropoffDateTime(LocalDateTime.now().plusDays(2));

        ReservationDTO created = reservationService.createReservation(dto);

        List<ReservationDTO> all = reservationService.getAllReservations();
        assertFalse(all.isEmpty());

        boolean exists = all.stream().anyMatch(r -> r.getId().equals(created.getId()));
        assertTrue(exists);
    }

    @Test
    void shouldDeleteReservation() {
        Location loc = new Location();
        loc.setCode("DEL_RES_LOC");
        loc.setName("Delete Res Loc");
        locationRepository.save(loc);

        Car car = new Car();
        car.setBarcode("DEL_RES_CAR");
        car.setLicensePlate("34 DEL 34");
        car.setStatus(CarStatus.AVAILABLE);
        car.setCategory(CarCategory.COMPACT);
        car.setTransmission(TransmissionType.MANUAL);
        car.setDailyPrice(BigDecimal.ONE);
        car.setCurrentLocation(loc);
        carRepository.save(car);

        Member member = new Member();
        member.setName("Delete User");
        member.setDrivingLicenseNumber("DEL_LICENSE");
        memberRepository.save(member);

        ReservationDTO dto = new ReservationDTO();
        dto.setCarBarcode("DEL_RES_CAR");
        dto.setMemberDrivingLicenseNumber("DEL_LICENSE");
        dto.setPickupLocationCode("DEL_RES_LOC");
        dto.setDropoffLocationCode("DEL_RES_LOC");
        dto.setPickupDateTime(LocalDateTime.now().plusDays(1));
        dto.setDropoffDateTime(LocalDateTime.now().plusDays(2));

        ReservationDTO created = reservationService.createReservation(dto);
        Long id = created.getId();

        reservationService.deleteReservationById(id);

        List<ReservationDTO> all = reservationService.getAllReservations();
        boolean exists = all.stream().anyMatch(r -> r.getId().equals(id));
        assertFalse(exists);
    }
}
