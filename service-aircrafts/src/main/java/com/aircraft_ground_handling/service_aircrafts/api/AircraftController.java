package com.aircraft_ground_handling.service_aircrafts.api;

import com.aircraft_ground_handling.service_aircrafts.api.dto.AircraftDTO;
import com.aircraft_ground_handling.service_aircrafts.repo.model.Aircraft;
import com.aircraft_ground_handling.service_aircrafts.repo.model.AircraftType;
import com.aircraft_ground_handling.service_aircrafts.service.AircraftService;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/aircrafts")
public class AircraftController {
    private final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<JSONObject> handleConstraintViolationException(ConstraintViolationException exception) {
        JSONObject response = new JSONObject();
        response.put("error", "incorrect request data");
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping
    public ResponseEntity<List<Aircraft>> getAllAircrafts() {
        List<Aircraft> aircrafts = aircraftService.getAll();

        return ResponseEntity.ok(aircrafts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aircraft> getAircraftByID(@PathVariable int id) {
        try {
            Aircraft aircraft = aircraftService.getByID(id);

            return ResponseEntity.ok(aircraft);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<JSONObject> createAircraft(@RequestBody AircraftDTO aircraft) {
        try {
            AircraftType aircraftType = AircraftType.valueOf(aircraft.getAircraftType());
            String aircraftNumber = aircraft.getAircraftNumber();

            int id = aircraftService.create(aircraftType, aircraftNumber);

            return ResponseEntity.created(URI.create("/aircrafts/" + id)).build();
        } catch (IllegalArgumentException | NullPointerException e) {
            JSONObject response = new JSONObject();
            response.put("error", "incorrect request data");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraft(@PathVariable int id) {
        aircraftService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
