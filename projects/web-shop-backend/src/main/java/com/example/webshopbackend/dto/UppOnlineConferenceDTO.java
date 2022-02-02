package com.example.webshopbackend.dto;

import java.time.LocalDateTime;

public class UppOnlineConferenceDTO {

    String name;
    Double price;
    LocalDateTime time;
    boolean course;
    String accommodation_name;
    Double accommodation_price;
    String accommodation_address;
    int accommodation_days;
    String transport_name;
    Double transport_price;

    public UppOnlineConferenceDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public boolean isCourse() {
        return course;
    }

    public void setCourse(boolean course) {
        this.course = course;
    }

    public String getAccommodation_name() {
        return accommodation_name;
    }

    public void setAccommodation_name(String accommodation_name) {
        this.accommodation_name = accommodation_name;
    }

    public Double getAccommodation_price() {
        return accommodation_price;
    }

    public void setAccommodation_price(Double accommodation_price) {
        this.accommodation_price = accommodation_price;
    }


    public String getTransport_name() {
        return transport_name;
    }

    public void setTransport_name(String transport_name) {
        this.transport_name = transport_name;
    }

    public String getAccommodation_address() {
        return accommodation_address;
    }

    public void setAccommodation_address(String accommodation_address) {
        this.accommodation_address = accommodation_address;
    }

    public int getAccommodation_days() {
        return accommodation_days;
    }

    public void setAccommodation_days(int accommodation_days) {
        this.accommodation_days = accommodation_days;
    }

    public Double getTransport_price() {
        return transport_price;
    }

    public void setTransport_price(Double transport_price) {
        this.transport_price = transport_price;
    }
}
