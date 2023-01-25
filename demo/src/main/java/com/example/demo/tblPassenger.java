package com.example.demo;

public class tblPassenger {

    private String name;
    private String vehicleNo;
    private double noLiters;

    public tblPassenger() {
    }

    public tblPassenger(String name, String vehicleNo, double noLiters) {
        this.name = name;
        this.vehicleNo = vehicleNo;
        this.noLiters = noLiters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
