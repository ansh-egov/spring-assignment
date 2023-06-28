package com.example.freshers.Assignment.models;

public class UserSearchCriteria {
    private Long id;
    private String mobileNumber;

    public UserSearchCriteria(){

    }

    public UserSearchCriteria(Long id) {
        this.id = id;
    }

    public UserSearchCriteria(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public UserSearchCriteria(Long id, String mobileNumber) {
        this.id = id;
        this.mobileNumber = mobileNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
