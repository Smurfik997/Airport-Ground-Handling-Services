package com.aircraft_ground_handling.service_parking.service;

import com.aircraft_ground_handling.service_parking.repo.model.Parking;
import com.aircraft_ground_handling.service_parking.repo.model.ParkingStatus;
import com.aircraft_ground_handling.service_parking.repo.ParkingRepo;
import com.aircraft_ground_handling.service_parking.repo.model.ParkingType;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class ParkingService {
    public final ParkingRepo parkingRepo;

    public List<Parking> getAll() {
        List<Parking> parkings = parkingRepo.findAll();
        return parkings;
    }

    public Parking getByID(int id) throws IllegalArgumentException {
        Optional<Parking> parking = parkingRepo.findById(id);

        if (parking.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            return parking.get();
        }
    }

    public int create(ParkingType type, ParkingStatus status) throws IllegalArgumentException {
        if (type == null) {
            throw new IllegalArgumentException();
        }

        Parking parking = new Parking(type, status);
        Parking savedParking = parkingRepo.save(parking);

        return savedParking.getId();
    }

    public void updateStatus(int id, ParkingStatus status) throws IllegalArgumentException {
        Parking parking = getByID(id);
        if (status != null) parking.setStatus(status);
        parkingRepo.save(parking);
    }

    public void delete(int id) {
        try {
            parkingRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return;
        }
    }
}
