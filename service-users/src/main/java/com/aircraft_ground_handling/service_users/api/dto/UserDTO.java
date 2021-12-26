package com.aircraft_ground_handling.service_users.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class UserDTO {
    private String userType;
    private String name;
}
