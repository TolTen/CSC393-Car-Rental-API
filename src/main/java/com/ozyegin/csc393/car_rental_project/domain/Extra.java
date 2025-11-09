package com.ozyegin.csc393.car_rental_project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Extra {

    private String name;
    private BigDecimal price;

    
}