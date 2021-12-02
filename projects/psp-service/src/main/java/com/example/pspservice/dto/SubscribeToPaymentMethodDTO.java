package com.example.pspservice.dto;

public class SubscribeToPaymentMethodDTO {

    private String paymentId;
    private String merchantId;

    public SubscribeToPaymentMethodDTO() {
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
}
