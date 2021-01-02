package com.soa.emergencyservice.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Contact {

    @Id
    @GeneratedValue
    private Integer id;

    private String userId;

    private String toEmail;

    private String emergency_phone;

    private String identity;

    public Contact() {
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmergency_phone() {
        return emergency_phone;
    }

    public void setEmergency_phone(String emergency_phone) {
        this.emergency_phone = emergency_phone;
    }
}
