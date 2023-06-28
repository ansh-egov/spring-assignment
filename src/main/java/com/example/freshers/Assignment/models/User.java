package com.example.freshers.Assignment.models;

public class User {
    private Long id;
    private String name;
    private String gender;
    private String mobileNumber;
    private String address;

    public User(){

    }

    public User(String name, String gender, String mobileNumber, String address) {
        this.name = name;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.address = address;
    }

    public User(Long id, String name, String gender, String mobileNumber, String address) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
