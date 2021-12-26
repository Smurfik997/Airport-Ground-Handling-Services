package com.aircraft_ground_handling.service_parking.service;

import com.aircraft_ground_handling.service_parking.repo.ParkingRequestRepo;
import com.aircraft_ground_handling.service_parking.repo.model.ParkingRequest;
import com.aircraft_ground_handling.service_parking.repo.model.ParkingType;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class ParkingRequestService {
    public final ParkingRequestRepo parkingRequestRepoRepo;

    public List<ParkingRequest> getAll() {
        List<ParkingRequest> parkingRequests = parkingRequestRepoRepo.findAll();
        return parkingRequests;
    }

    public double getPrice(ParkingType parkingType, int parkingTime, String aircraftType) {
        double parkingPrice = 0;
        double coefficient = parkingType.ordinal() * 0.5 / 3 + 1.0;

        switch (aircraftType) {
            case "PRIVATE":
                parkingPrice = 10 * parkingTime * coefficient;
                break;
            case "CARGO":
                parkingPrice = 30 * parkingTime * coefficient;
                break;
            case "COMMERCIAL":
                parkingPrice = 50 * parkingTime * coefficient;
                break;
        }

        return parkingPrice;
    }

    public ParkingRequest getByID(int id) throws IllegalArgumentException {
        Optional<ParkingRequest> parkingRequest = parkingRequestRepoRepo.findById(id);

        if (parkingRequest.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            return parkingRequest.get();
        }
    }

    public int create(int aircraftID, int parkingTime, int parkingID, double parkingPrice) throws IllegalArgumentException {
        ParkingRequest parkingRequest = new ParkingRequest(aircraftID, parkingTime, parkingID, parkingPrice);
        ParkingRequest savedParkingRequest = parkingRequestRepoRepo.save(parkingRequest);

        return savedParkingRequest.getId();
    }

    public void update(int id, int parkingID, int parkingPrice) throws IllegalArgumentException {
        ParkingRequest parkingRequest = getByID(id);
        if (parkingID != 0) parkingRequest.setParkingID(parkingID);
        if (parkingPrice != 0) parkingRequest.setParkingPrice(parkingPrice);
        parkingRequestRepoRepo.save(parkingRequest);
    }

    public void delete(int id) {
        try {
            parkingRequestRepoRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return;
        }
    }
}
