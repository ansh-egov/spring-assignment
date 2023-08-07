package com.example.freshers.Assignment.models;

import java.util.UUID;

public class User {
    private String id;
    private String name;
    private String gender;
    private String mobileNumber;
    private Address address;
    private  String isActive;
    private  Long createdTime;

    public User(){

    }

    public User(String name, String gender, String mobileNumber, Address address,String isActive, Long createdTime) {
        this.name = name;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.isActive = isActive;
        this.createdTime = createdTime;
    }

    public User(String name, String gender, String mobileNumber, Address address,String isActive) {
        this.name = name;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.isActive = isActive;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", address='" + address + '\'' +
                ", isActive='" + isActive + '\'' +
                ", createdTime='" + createdTime + '\'' +
                '}';
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }
}
