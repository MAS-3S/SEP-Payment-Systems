package com.example.webshopbackend.dto;

import java.time.LocalDateTime;

public class RequestPaymentDTO {
    private String merchantId;
    private String transactionId;
    private Double amount;
    private String currency;
    private LocalDateTime timestamp;
    private boolean isPossibleSubscription;

    public RequestPaymentDTO() {
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isPossibleSubscription() {
        return isPossibleSubscription;
    }

    public void setPossibleSubscription(boolean possibleSubscription) {
        isPossibleSubscription = possibleSubscription;
    }
}
