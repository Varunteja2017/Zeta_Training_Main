package com.zeta.basic_spring.entity;

import jakarta.validation.constraints.Min;

public class OrderRequest {
    String houseNumber;
    @Min(100000)
    int pinCode;

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }
}
