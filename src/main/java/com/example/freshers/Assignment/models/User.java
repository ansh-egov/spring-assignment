package com.example.freshers.Assignment.models;

public class User {
    private Long id;
    private String name;
    private String gender;
    private String mobileNumber;
    private String address;
    private  String is_active;

    public User(){

    }

    public User(String name, String gender, String mobileNumber, String address,String is_active) {
        this.name = name;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.is_active = is_active;
    }

    public User(Long id, String name, String gender, String mobileNumber, String address,String is_active) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.is_active = is_active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", address='" + address + '\'' +
                ", is_active='" + is_active + '\'' +
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

    public String isIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }
}
