package com.aircraft_ground_handling.service_parking.repo.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "parkings")
public final class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(columnDefinition = "ENUM('APRON', 'GATE', 'HANGAR')")
    @Enumerated(EnumType.STRING)
    @NotNull
    private ParkingType type;

    @Column(columnDefinition = "ENUM('FREE', 'OCCUPIED')")
    @Enumerated(EnumType.STRING)
    @NotNull
    private ParkingStatus status;
    public Parking() {
    }

    public Parking(ParkingType type, ParkingStatus status) {
        this.type = type;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ParkingType getType() {
        return type;
    }

    public void setType(ParkingType type) {
        this.type = type;
    }

    public ParkingStatus getStatus() {
        return status;
    }

    public void setStatus(ParkingStatus status) {
        this.status = status;
    }
}
