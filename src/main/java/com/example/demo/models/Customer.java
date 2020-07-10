package com.example.demo.models;

public class Customer {
    private String CPR;
    private String name;
    private String driversLicenseID;
    private String creditCardNo;

    public Customer(String CPR, String name, String driversLicenseID, String creditCardNo) {
        this.CPR = CPR;
        this.name = name;
        this.driversLicenseID = driversLicenseID;
        this.creditCardNo = creditCardNo;
    }

    public Customer(){}

    public String getCPR() {
        return CPR;
    }

    public void setCPR(String CPR) {
        this.CPR = CPR;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDriversLicenseID() {
        return driversLicenseID;
    }

    public void setDriversLicenseID(String driversLicensID) {
        this.driversLicenseID = driversLicensID;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }
}
