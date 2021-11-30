package com.example.pspservice.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "merchants")
public class Merchant {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private String merchantId;

    @Column(nullable = false)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    List<SubscriptionType> subscriptionTypes;


    private String returnUrl;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public List<SubscriptionType> getSubscriptionTypeList() {
        return subscriptionTypes;
    }

    public void setSubscriptionTypeList(List<SubscriptionType> subscriptionTypeList) {
        this.subscriptionTypes = subscriptionTypeList;
    }
}
