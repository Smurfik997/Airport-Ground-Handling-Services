package com.aircraft_ground_handling.service_aircrafts.repo;

import com.aircraft_ground_handling.service_aircrafts.repo.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftRepo extends JpaRepository<Aircraft, Integer> {
}
