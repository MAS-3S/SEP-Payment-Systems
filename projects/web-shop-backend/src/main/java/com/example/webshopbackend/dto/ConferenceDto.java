package com.example.webshopbackend.dto;

import javax.persistence.Column;
import java.util.Date;

public class ConferenceDto {

    private String name;
    private String description;
    private String image;
    private Double price;
    private int availableBalance;
    private String currency;
    private String address;
    private Date startTime;
    private Date endTime;
    private Boolean isCourse;
    private Boolean isSubscription;

    public ConferenceDto() {
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getCourse() {
        return isCourse;
    }

    public void setCourse(Boolean course) {
        isCourse = course;
    }

    public Boolean getSubscription() {
        return isSubscription;
    }

    public void setSubscription(Boolean subscription) {
        isSubscription = subscription;
    }
}
