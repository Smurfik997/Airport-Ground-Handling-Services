package com.aircraft_ground_handling.service_parking.api;

import com.aircraft_ground_handling.service_parking.api.dto.ParkingRequestDTO;
import com.aircraft_ground_handling.service_parking.repo.model.ParkingRequest;
import com.aircraft_ground_handling.service_parking.repo.model.ParkingType;
import com.aircraft_ground_handling.service_parking.service.ParkingRequestService;
import org.json.simple.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/requests")
public class ParkingRequestController {
    private final ParkingRequestService parkingRequestService;
    private final String aircraftService = "http://localhost:8082";
    private String parkingService = "http://localhost:8084";

    public ParkingRequestController(ParkingRequestService parkingRequestService) {
        this.parkingRequestService = parkingRequestService;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<JSONObject> handleConstraintViolationException(ConstraintViolationException exception) {
        JSONObject response = new JSONObject();
        response.put("error", "incorrect request data");
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping
    public ResponseEntity<List<ParkingRequest>> getAllParkingRequests() {
        List<ParkingRequest> parkingRequests = parkingRequestService.getAll();

        return ResponseEntity.ok(parkingRequests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingRequest> getParkingRequestByID(@PathVariable int id) {
        try {
            ParkingRequest parkingRequest = parkingRequestService.getByID(id);

            return ResponseEntity.ok(parkingRequest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/price")
    public ResponseEntity<JSONObject> getParkingRequestByID(@RequestBody ParkingRequestDTO parkingRequest) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            int aircraftID = parkingRequest.getAircraftID();
            ResponseEntity<JSONObject> aircraftInfo = restTemplate.exchange(
                    aircraftService + "/aircrafts/" + aircraftID, HttpMethod.GET, null, JSONObject.class
            );
            int parkingTime = parkingRequest.getParkingTime();
            int parkingID = parkingRequest.getParkingID();
            ResponseEntity<JSONObject> parkingInfo = restTemplate.exchange(
                    parkingService + "/parkings/" + parkingID, HttpMethod.GET, null, JSONObject.class
            );

            String aircraftType = (String) aircraftInfo.getBody().get("aircraftType");
            ParkingType parkingType = ParkingType.valueOf((String) parkingInfo.getBody().get("type"));

            double parkingPrice = parkingRequestService.getPrice(parkingType, parkingTime, aircraftType);

            JSONObject response = new JSONObject();
            response.put("price", parkingPrice);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.notFound().build();
        } catch (HttpClientErrorException e) {
            JSONObject response = new JSONObject();
            response.put("error", "incorrect aircraftID and/or parkingID");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<JSONObject> createParking(@RequestBody ParkingRequestDTO parkingRequest) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            int aircraftID = parkingRequest.getAircraftID();
            ResponseEntity<JSONObject> aircraftInfo = restTemplate.exchange(
                    aircraftService + "/aircrafts/" + aircraftID, HttpMethod.GET, null, JSONObject.class
            );
            int parkingTime = parkingRequest.getParkingTime();
            int parkingID = parkingRequest.getParkingID();
            ResponseEntity<JSONObject> parkingInfo = null;
            if (parkingID != 0) {
                parkingInfo = restTemplate.exchange(
                        parkingService + "/parkings/" + parkingID, HttpMethod.GET, null, JSONObject.class
                );
            }
            double parkingPrice = parkingRequest.getParkingPrice();
            if (parkingPrice == 0 && parkingID != 0) {
                String aircraftType = (String) aircraftInfo.getBody().get("aircraftType");
                ParkingType parkingType = ParkingType.valueOf((String) parkingInfo.getBody().get("type"));

                parkingPrice = parkingRequestService.getPrice(parkingType, parkingTime, aircraftType);
            }

            int id = parkingRequestService.create(aircraftID, parkingTime, parkingID, parkingPrice);

            return ResponseEntity.created(URI.create("/requests/" + id)).build();
        } catch (IllegalArgumentException | NullPointerException e) {
            JSONObject response = new JSONObject();
            response.put("error", "incorrect request data");
            return ResponseEntity.badRequest().body(response);
        } catch (HttpClientErrorException e) {
            JSONObject response = new JSONObject();
            response.put("error", "incorrect aircraftID and/or parkingID");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<JSONObject> update(@PathVariable int id, @RequestBody ParkingRequestDTO parkingRequest) {
        try {
            int parkingID = parkingRequest.getParkingID();
            if (parkingID != 0) {
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<JSONObject> response = restTemplate.exchange(
                        parkingService + "/parkings/" + parkingID, HttpMethod.GET, null, JSONObject.class
                );
            }
            int parkingPrice = parkingRequest.getParkingPrice();

            parkingRequestService.update(id, parkingID, parkingPrice);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException | NullPointerException e) {
            JSONObject response = new JSONObject();
            response.put("error", "incorrect request data");
            return ResponseEntity.badRequest().body(response);
        } catch (HttpClientErrorException e) {
            JSONObject response = new JSONObject();
            response.put("error", "incorrect parking ID");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingRequest(@PathVariable int id) {
        parkingRequestService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
