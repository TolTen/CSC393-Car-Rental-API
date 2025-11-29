package com.ozyegin.csc393.car_rental_project.dto;

import com.ozyegin.csc393.car_rental_project.enums.ReservationStatus;
import lombok.Data;
import java.time.LocalDateTime;

//@Data
public class ReservationDTO {
    private Long id;
    private String reservationNumber;
    private LocalDateTime createdDate;
    private LocalDateTime pickupDateTime;
    private LocalDateTime dropoffDateTime;
    private LocalDateTime returnDateTime;
    private ReservationStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getPickupDateTime() {
        return pickupDateTime;
    }

    public void setPickupDateTime(LocalDateTime pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
    }

    public LocalDateTime getDropoffDateTime() {
        return dropoffDateTime;
    }

    public void setDropoffDateTime(LocalDateTime dropoffDateTime) {
        this.dropoffDateTime = dropoffDateTime;
    }

    public LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }

    public void setReturnDateTime(LocalDateTime returnDateTime) {
        this.returnDateTime = returnDateTime;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public String getCarBarcode() {
        return carBarcode;
    }

    public void setCarBarcode(String carBarcode) {
        this.carBarcode = carBarcode;
    }

    public String getMemberDrivingLicenseNumber() {
        return memberDrivingLicenseNumber;
    }

    public void setMemberDrivingLicenseNumber(String memberDrivingLicenseNumber) {
        this.memberDrivingLicenseNumber = memberDrivingLicenseNumber;
    }

    public String getPickupLocationCode() {
        return pickupLocationCode;
    }

    public void setPickupLocationCode(String pickupLocationCode) {
        this.pickupLocationCode = pickupLocationCode;
    }

    public String getDropoffLocationCode() {
        return dropoffLocationCode;
    }

    public void setDropoffLocationCode(String dropoffLocationCode) {
        this.dropoffLocationCode = dropoffLocationCode;
    }

    private String carBarcode;
    private String memberDrivingLicenseNumber;
    private String pickupLocationCode;
    private String dropoffLocationCode;
}