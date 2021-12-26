package com.aircraft_ground_handling.service_requests.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestDTO {
    private String requestType;
    private int details;
    private int producer;
    private int consumer;
    private String requestStatus;
}
