package com.example.webshopbackend.dto;

public class UppOfferItemDto {
    int price;
    UppEquipmentDto equipment;

    public UppOfferItemDto() {
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public UppEquipmentDto getEquipment() {
        return equipment;
    }

    public void setEquipment(UppEquipmentDto equipment) {
        this.equipment = equipment;
    }
}
