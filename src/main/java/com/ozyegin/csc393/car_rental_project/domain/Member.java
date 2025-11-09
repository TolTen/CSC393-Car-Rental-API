package com.ozyegin.csc393.car_rental_project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    private String name;
    private String address;
    private String email;
    private String phone;
    private String drivingLicenseNumber;


}