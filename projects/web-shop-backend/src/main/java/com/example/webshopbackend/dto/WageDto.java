package com.example.webshopbackend.dto;

public class WageDto {

    private String webShopId;
    private String accountNumber;
    private String bankNumber;
    private Double amount;
    private String currency;

    public WageDto() {
    }

    public String getWebShopId() {
        return webShopId;
    }

    public void setWebShopId(String webShopId) {
        this.webShopId = webShopId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
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
}
