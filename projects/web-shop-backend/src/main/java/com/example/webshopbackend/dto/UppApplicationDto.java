package com.example.webshopbackend.dto;

public class UppApplicationDto {

    private String mostCommonCity;
    private int mostCommonTime;

    public UppApplicationDto() {
    }

    public String getMostCommonCity() {
        return mostCommonCity;
    }

    public void setMostCommonCity(String mostCommonCity) {
        this.mostCommonCity = mostCommonCity;
    }

    public int getMostCommonTime() {
        return mostCommonTime;
    }

    public void setMostCommonTime(int mostCommonTime) {
        this.mostCommonTime = mostCommonTime;
    }
}
