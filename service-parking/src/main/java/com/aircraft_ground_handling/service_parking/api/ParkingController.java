package com.aircraft_ground_handling.service_parking.api;

import com.aircraft_ground_handling.service_parking.api.dto.ParkingDTO;
import com.aircraft_ground_handling.service_parking.repo.model.Parking;
import com.aircraft_ground_handling.service_parking.repo.model.ParkingStatus;
import com.aircraft_ground_handling.service_parking.repo.model.ParkingType;
import com.aircraft_ground_handling.service_parking.service.ParkingService;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/parkings")
public class ParkingController {
    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<JSONObject> handleConstraintViolationException(ConstraintViolationException exception) {
        JSONObject response = new JSONObject();
        response.put("error", "incorrect request data");
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping
    public ResponseEntity<List<Parking>> getAllParkings() {
        List<Parking> parkings = parkingService.getAll();

        return ResponseEntity.ok(parkings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parking> getParkingByID(@PathVariable int id) {
        try {
            Parking parking = parkingService.getByID(id);

            return ResponseEntity.ok(parking);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<JSONObject> createParking(@RequestBody ParkingDTO parking) {
        try {
            ParkingType parkingType = ParkingType.valueOf(parking.getType());
            ParkingStatus parkingStatus = ParkingStatus.FREE;

            if (parking.getStatus() != null && !parking.getStatus().isBlank()) {
                parkingStatus = ParkingStatus.valueOf(parking.getStatus());
            }

            int id = parkingService.create(parkingType, parkingStatus);

            return ResponseEntity.created(URI.create("/parkings/" + id)).build();
        } catch (IllegalArgumentException | NullPointerException e) {
            JSONObject response = new JSONObject();
            response.put("error", "incorrect request data");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<JSONObject> update(@PathVariable int id, @RequestBody ParkingDTO parking) {
        try {
            ParkingStatus parkingStatus = ParkingStatus.valueOf(parking.getStatus());
            parkingService.updateStatus(id, parkingStatus);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException | NullPointerException e) {
            JSONObject response = new JSONObject();
            response.put("error", "incorrect request data");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParking(@PathVariable int id) {
        parkingService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
