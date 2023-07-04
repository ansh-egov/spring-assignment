package com.example.freshers.Assignment.models;

import java.util.UUID;

public class UserSearchCriteria {
    private UUID id;
    private String mobileNumber;

    public UserSearchCriteria(){

    }

    public UserSearchCriteria(UUID id) {
        this.id = id;
    }

    public UserSearchCriteria(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public UserSearchCriteria(UUID id, String mobileNumber) {
        this.id = id;
        this.mobileNumber = mobileNumber;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public String toString() {
        return "UserSearchCriteria{" +
                "id=" + id +
                ", mobileNumber='" + mobileNumber + '\'' +
                '}';
    }
}
