package com.example.pspservice.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subscription_types")
public class SubscriptionType {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "subscriptionType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transaction> transactionList;


    public SubscriptionType() {
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

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}
