package com.example.paypalservice.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @ManyToOne
    private Merchant merchant;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = true)
    private Long payPalOrderId;

    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String currency;

    private boolean isPossibleSubscription;

    @Column(nullable = false)
    private String timestamp;

    @Column(nullable = false)
    private String returnUrl;

    @Column(nullable = false)
    private String successUrl;

    @Column(nullable = false)
    private String cancelUrl;


    public Transaction() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getPayPalOrderId() {
        return payPalOrderId;
    }

    public void setPayPalOrderId(Long payPalOrderId) {
        this.payPalOrderId = payPalOrderId;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
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

    public boolean isPossibleSubscription() {
        return isPossibleSubscription;
    }

    public void setPossibleSubscription(boolean possibleSubscription) {
        isPossibleSubscription = possibleSubscription;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
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
