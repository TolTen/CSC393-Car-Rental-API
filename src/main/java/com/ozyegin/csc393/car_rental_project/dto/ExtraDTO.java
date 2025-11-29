package com.ozyegin.csc393.car_rental_project.dto;

import lombok.Data;
import java.math.BigDecimal;

//@Data
public class ExtraDTO {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    private Long id;
    private String name;
    private BigDecimal price;
}