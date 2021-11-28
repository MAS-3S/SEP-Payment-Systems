package com.example.webshopbackend.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="conferences")
public class Conference extends Product {

//    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
//    @Column(name = "id", updatable = false, nullable = false)
//    private String id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "end_time", nullable = false)
    private Date endTime;

    @Column(name = "is_course", nullable = false)
    private Boolean isCourse;

    @Column(name = "is_subscription", nullable = false)
    private Boolean isSubscription;

    public Conference() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getCourse() {
        return isCourse;
    }

    public void setCourse(Boolean course) {
        isCourse = course;
    }

    public Boolean getSubscription() {
        return isSubscription;
    }

    public void setSubscription(Boolean subscription) {
        isSubscription = subscription;
    }
}
