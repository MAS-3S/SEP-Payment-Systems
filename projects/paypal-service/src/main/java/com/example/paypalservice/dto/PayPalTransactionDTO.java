package com.example.paypalservice.dto;

public class PayPalTransactionDTO {

    private String clientId;
    private String currency;
    private Double amount;
    private String successUrl;
    private String cancelUrl;

    public PayPalTransactionDTO() {
    }

    public PayPalTransactionDTO(String clientId, String currency, Double amount, String successUrl, String cancelUrl) {
        this.clientId = clientId;
        this.currency = currency;
        this.amount = amount;
        this.successUrl = successUrl;
        this.cancelUrl = cancelUrl;
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
}
