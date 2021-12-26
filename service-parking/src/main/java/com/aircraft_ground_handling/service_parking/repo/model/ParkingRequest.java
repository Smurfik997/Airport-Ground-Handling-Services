package com.aircraft_ground_handling.service_parking.repo.model;

import javax.validation.constraints.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "parkingsRequests")
public final class ParkingRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private int aircraftID;

    @NotNull
    private int parkingTime;

    private int parkingID;

    @NotNull
    private double parkingPrice;

    public ParkingRequest() {
    }

    public ParkingRequest(int aircraftID, int parkingTime, int parkingID, double parkingPrice) {
        this.aircraftID = aircraftID;
        this.parkingTime = parkingTime;
        this.parkingID = parkingID;
        this.parkingPrice = parkingPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAircraftID() {
        return aircraftID;
    }

    public void setAircraftID(int aircraftID) {
        this.aircraftID = aircraftID;
    }

    public int getParkingTime() {
        return parkingTime;
    }

    public void setParkingTime(int parkingTime) {
        this.parkingTime = parkingTime;
    }

    public int getParkingID() {
        return parkingID;
    }

    public void setParkingID(int parkingID) {
        this.parkingID = parkingID;
    }

    public double getParkingPrice() {
        return parkingPrice;
    }

    public void setParkingPrice(int parkingPrice) {
        this.parkingPrice = parkingPrice;
    }
}
