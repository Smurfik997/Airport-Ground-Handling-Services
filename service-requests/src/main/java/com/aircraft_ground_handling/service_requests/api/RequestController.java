package com.aircraft_ground_handling.service_requests.api;

import com.aircraft_ground_handling.service_requests.api.dto.RequestDTO;
import com.aircraft_ground_handling.service_requests.repo.model.Request;
import com.aircraft_ground_handling.service_requests.repo.model.RequestStatus;
import com.aircraft_ground_handling.service_requests.repo.model.RequestType;
import com.aircraft_ground_handling.service_requests.service.RequestService;
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
public class RequestController {
    private final RequestService requestService;
    private final String userService = "http://service-users:8081";

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<JSONObject> handleConstraintViolationException(ConstraintViolationException exception) {
        JSONObject response = new JSONObject();
        response.put("error", "incorrect request data");
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping
    public ResponseEntity<List<Request>> getAllRequests() {
        List<Request> requests = requestService.getAll();

        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequestByID(@PathVariable int id) {
        try {
            Request request = requestService.getByID(id);

            return ResponseEntity.ok(request);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<JSONObject> createRequest(@RequestBody RequestDTO request) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            RequestType requestType = RequestType.valueOf(request.getRequestType());
            int details = request.getDetails();
            int producer = request.getProducer();
            int consumer = request.getConsumer();

            if (producer != 0) {
                ResponseEntity<JSONObject> response = restTemplate.exchange(
                        userService + "/users/" + producer, HttpMethod.GET, null, JSONObject.class
                );
            }

            if (consumer != 0) {
                ResponseEntity<JSONObject> response = restTemplate.exchange(
                        userService + "/users/" + consumer, HttpMethod.GET, null, JSONObject.class
                );
            }

            RequestStatus requestStatus = RequestStatus.valueOf(request.getRequestStatus());

            int id = requestService.create(requestType, details, producer, consumer, requestStatus);

            return ResponseEntity.created(URI.create("/requests/" + id)).build();
        } catch (IllegalArgumentException | NullPointerException e) {
            JSONObject response = new JSONObject();
            response.put("error", "incorrect request data");
            return ResponseEntity.badRequest().body(response);
        } catch (HttpClientErrorException.NotFound e) {
            JSONObject response = new JSONObject();
            response.put("error", "incorrect consumer or/and producer id");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<JSONObject> update(@PathVariable int id, @RequestBody RequestDTO request) {
        try {
            RequestStatus requestStatus = null;

            if (request.getRequestStatus() != null && !request.getRequestStatus().isBlank()) {
                requestStatus = RequestStatus.valueOf(request.getRequestStatus());
            }

            int consumerID = request.getConsumer();

            if (consumerID != 0) {
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<JSONObject> response = restTemplate.exchange(
                        userService + "/users/" + consumerID, HttpMethod.GET, null, JSONObject.class
                );
            }

            requestService.update(id, requestStatus, consumerID);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException | NullPointerException e) {
            JSONObject response = new JSONObject();
            response.put("error", "incorrect request data");
            return ResponseEntity.badRequest().body(response);
        } catch (HttpClientErrorException.NotFound e) {
            JSONObject response = new JSONObject();
            response.put("error", "incorrect consumer id");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable int id) {
        requestService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
