package com.example.webshopbackend.dto;

import java.util.Date;

public class AccommodationDto {

    private String name;
    private String description;
    private String image;
    private Double price;
    private int availableBalance;
    private String address;
    private Date startDate;
    private int days;
    private int numberOfBads;
    private String transportName;
    private Double transportPrice;

    public AccommodationDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(int availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getNumberOfBads() {
        return numberOfBads;
    }

    public void setNumberOfBads(int numberOfBads) {
        this.numberOfBads = numberOfBads;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public Double getTransportPrice() {
        return transportPrice;
    }

    public void setTransportPrice(Double transportPrice) {
        this.transportPrice = transportPrice;
    }
}
