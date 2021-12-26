package com.aircraft_ground_handling.service_parking.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParkingRequestDTO {
    private int aircraftID;
    private int parkingTime;
    private int parkingID;
    private int parkingPrice;
}
