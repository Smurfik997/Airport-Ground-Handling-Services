package com.aircraft_ground_handling.service_parking.repo;

import com.aircraft_ground_handling.service_parking.repo.model.ParkingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRequestRepo  extends JpaRepository<ParkingRequest, Integer> {
}
