package com.example.webshopbackend.dto;

public class UppDto {

    private Double price;
    private boolean isSuccess;

    public UppDto() {
        isSuccess = false;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
