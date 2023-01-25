package com.example.demo;

public class Passenger {

    private String firstName;
    private String secondName;
    private String vehicleNo;
    private double noLiters;

    public Passenger() {
    }

    public Passenger(String firstName, String secondName, String vehicleNo, double noLiters) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.vehicleNo = vehicleNo;
        this.noLiters = noLiters;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public double getNoLiters() {
        return noLiters;
    }

    public void setNoLiters(double noLiters) {
        this.noLiters = noLiters;
    }
}

