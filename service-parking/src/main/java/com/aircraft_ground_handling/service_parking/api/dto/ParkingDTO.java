package com.aircraft_ground_handling.service_parking.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class ParkingDTO {
    private String type;
    private String status;
}
