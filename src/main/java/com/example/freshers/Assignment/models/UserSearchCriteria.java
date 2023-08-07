package com.example.freshers.Assignment.models;

import java.util.UUID;

public class UserSearchCriteria {
    private String id;
    private String mobileNumber;

    public UserSearchCriteria(){

    }

    public UserSearchCriteria(String id) {
        this.id = id;
    }

    public UserSearchCriteria(String id, String mobileNumber) {
        this.id = id;
        this.mobileNumber = mobileNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
