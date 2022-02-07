package com.example.webshopbackend.dto;

public class UppEquipmentDto {
    UppEquipmentTypeDto type;
    String name;
    int quantity;
    int price;
    boolean isSuccess;

    public UppEquipmentDto() {
    }

    public UppEquipmentTypeDto getType() {
        return type;
    }

    public void setType(UppEquipmentTypeDto type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
