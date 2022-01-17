package com.example.paypalservice.dto;

public class PayPalTransactionDTO {

    private String transactionId;
    private String clientId;
    private String currency;
    private boolean isPossibleSubscription;
    private Double amount;
    private String successUrl;
    private String cancelUrl;

    public PayPalTransactionDTO() {
    }

    public PayPalTransactionDTO(String transactionId, String clientId, String currency, Double amount, String successUrl, String cancelUrl, boolean isPossibleSubscription) {
        this.transactionId = transactionId;
        this.clientId = clientId;
        this.currency = currency;
        this.amount = amount;
        this.successUrl = successUrl;
        this.cancelUrl = cancelUrl;
        this.isPossibleSubscription = isPossibleSubscription;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public boolean isPossibleSubscription() {
        return isPossibleSubscription;
    }

    public void setPossibleSubscription(boolean possibleSubscription) {
        isPossibleSubscription = possibleSubscription;
    }
}
