package com.aircraft_ground_handling.service_aircrafts.repo.model;

import javax.validation.constraints.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "aircrafts")
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(columnDefinition = "ENUM('PRIVATE', 'CARGO', 'COMMERCIAL')")
    @Enumerated(EnumType.STRING)
    @NotNull
    private AircraftType aircraftType;

    @NotNull
    private String aircraftNumber;

    public Aircraft() {
    }

    public Aircraft(AircraftType aircraftType, String aircraftNumber) {
        this.aircraftType = aircraftType;
        this.aircraftNumber = aircraftNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AircraftType getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(AircraftType aircraftType) {
        this.aircraftType = aircraftType;
    }

    public String getAircraftNumber() {
        return aircraftNumber;
    }

    public void setAircraftNumber(String aircraftNumber) {
        this.aircraftNumber = aircraftNumber;
    }
}
