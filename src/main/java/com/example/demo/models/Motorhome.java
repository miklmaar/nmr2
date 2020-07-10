package com.example.demo.models;

public class Motorhome {
    private int motorhomeID;
    private String licensePlate;
    private String modelName;
    private int numberOfBeds;
    private double pricePerNight;
    private boolean cleaned;
    private boolean repaired;
    private boolean active;

    public Motorhome(int motorhomeID, String licensePlate, String modelName, int numberOfBeds, double pricePerNight, boolean cleaned, boolean repaired, boolean active) {
        this.motorhomeID = motorhomeID;
        this.licensePlate = licensePlate;
        this.modelName = modelName;
        this.numberOfBeds = numberOfBeds;
        this.pricePerNight = pricePerNight;
        this.cleaned = cleaned;
        this.repaired = repaired;
        this.active = active;
    }

    public Motorhome(){}

    public int getMotorhomeID() {
        return motorhomeID;
    }

    public void setMotorhomeID(int motorhomeID) {
        this.motorhomeID = motorhomeID;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public boolean isCleaned() {
        return cleaned;
    }

    public void setCleaned(boolean cleaned) {
        this.cleaned = cleaned;
    }

    public boolean isRepaired() {
        return repaired;
    }

    public void setRepaired(boolean repaired) {
        this.repaired = repaired;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
