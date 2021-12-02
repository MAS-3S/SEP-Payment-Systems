package com.example.pspservice.dto;

public class PaymentMethodTypeDTO {

    private String id;
    private String name;

    public PaymentMethodTypeDTO() {
    }

    public PaymentMethodTypeDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
