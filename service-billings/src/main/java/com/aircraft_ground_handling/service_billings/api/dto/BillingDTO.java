package com.aircraft_ground_handling.service_billings.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BillingDTO {
    private String billingServiceType;
    private String billingStatus;
    private int count;
    private double price;
    private double totalPrice;
    private int consumer;
}
