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
    List<PaymentMethodType> paymentMethodTypes;

    @Column(name="success_url")
    private String successUrl;

    @Column(name="failed_url")
    private String failedUrl;

    @Column(name="error_url")
    private String errorUrl;

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

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getFailedUrl() {
        return failedUrl;
    }

    public void setFailedUrl(String failedUrl) {
        this.failedUrl = failedUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    public List<PaymentMethodType> getPaymentMethodTypes() {
        return paymentMethodTypes;
    }

    public void setPaymentMethodTypes(List<PaymentMethodType> paymentMethodTypeList) {
        this.paymentMethodTypes = paymentMethodTypeList;
    }

    public void addToPaymentMethod(PaymentMethodType paymentMethodType) {
        this.paymentMethodTypes.add(paymentMethodType);
    }

    public void deleteFromPaymentMethod(PaymentMethodType paymentMethodType) {
        this.paymentMethodTypes.remove(paymentMethodType);
    }

}
