package com.aircraft_ground_handling.service_aircrafts.service;

import com.aircraft_ground_handling.service_aircrafts.repo.AircraftRepo;
import com.aircraft_ground_handling.service_aircrafts.repo.model.Aircraft;
import com.aircraft_ground_handling.service_aircrafts.repo.model.AircraftType;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AircraftService {
    public final AircraftRepo aircraftRepo;

    public List<Aircraft> getAll() {
        List<Aircraft> aircrafts = aircraftRepo.findAll();
        return aircrafts;
    }

    public Aircraft getByID(int id) throws IllegalArgumentException {
        Optional<Aircraft> aircraft = aircraftRepo.findById(id);

        if (aircraft.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            return aircraft.get();
        }
    }

    public int create(AircraftType aircraftType, String aircraftNumber) throws IllegalArgumentException {
        Aircraft aircraft = new Aircraft(aircraftType, aircraftNumber);
        Aircraft savedAircraft = aircraftRepo.save(aircraft);

        return savedAircraft.getId();
    }

    public void delete(int id) {
        try {
            aircraftRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return;
        }
    }
}
