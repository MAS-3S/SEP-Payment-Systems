package com.example.bankservice.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "merchants")
public class Merchant {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(nullable = false, unique = true)
    private String merchantId;

    @Column(nullable = false)
    private String bankMerchantId;

    @Column(nullable = false)
    private String bankMerchantPassword;

    @OneToMany(mappedBy = "merchant")
    private Set<Transaction> transactions;

    public Merchant() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getBankMerchantId() {
        return bankMerchantId;
    }

    public void setBankMerchantId(String bankMerchantId) {
        this.bankMerchantId = bankMerchantId;
    }

    public String getBankMerchantPassword() {
        return bankMerchantPassword;
    }

    public void setBankMerchantPassword(String bankMerchantPassword) {
        this.bankMerchantPassword = bankMerchantPassword;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}
