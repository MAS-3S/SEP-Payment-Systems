package com.example.pspservice.dto;

public class PaymentMethodTypeForMerchantDTO {

    private String id;
    private String name;
    private boolean isSubscribed;

    public PaymentMethodTypeForMerchantDTO() {
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

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }
}
