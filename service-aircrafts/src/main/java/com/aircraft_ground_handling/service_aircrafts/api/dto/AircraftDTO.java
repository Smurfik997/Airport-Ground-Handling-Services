package com.aircraft_ground_handling.service_aircrafts.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AircraftDTO {
    private String aircraftType;
    private String aircraftNumber;
}
