package com.example.demo.models;

import java.util.List;

public class Order {
    private int orderID;
    private Customer customer;
    //private Season season;
    private List<Motorhome> mhInOrder;
    private int numberOfDays;
    private boolean droppedOff;
    private int dropOffDistance;

    public Order(int orderID, Customer customer, List<Motorhome> mhInOrder, int numberOfDays, int dropOffDistance, boolean droppedOff) {
        this.orderID = orderID;
        this.customer = customer;
        this.mhInOrder = mhInOrder;
        this.numberOfDays = numberOfDays;
        this.droppedOff = droppedOff;
        this.dropOffDistance = dropOffDistance;
    }

    public Order(){};

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Motorhome> getMhInOrder() {
        return mhInOrder;
    }

    public void setMhInOrder(List<Motorhome> mhInOrder) {
        this.mhInOrder = mhInOrder;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public int getDropOffDistance() {
        return dropOffDistance;
    }

    public void setDropOffDistance(int dropOffDistance) {
        this.dropOffDistance = dropOffDistance;
    }

    public boolean isDroppedOff() {
        return droppedOff;
    }

    public void setDroppedOff(boolean droppedOff) {
        this.droppedOff = droppedOff;
    }




    public double calcTotalPrice(){
        double totalPrice = 0.0;
        for(Motorhome motorhome : mhInOrder){
            totalPrice += motorhome.getPricePerNight()*numberOfDays;
        }

        totalPrice += dropOffDistance * 0.7;

        return totalPrice;
    }

}