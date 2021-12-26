package com.aircraft_ground_handling.service_billings.api;

import com.aircraft_ground_handling.service_billings.api.dto.BillingDTO;
import com.aircraft_ground_handling.service_billings.repo.model.Billing;
import com.aircraft_ground_handling.service_billings.repo.model.BillingServiceType;
import com.aircraft_ground_handling.service_billings.repo.model.BillingStatus;
import com.aircraft_ground_handling.service_billings.service.BillingService;
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
@RequestMapping("/billings")
public class BillingController {
    private final BillingService billingService;
    private final String userService = "http://localhost:8081";

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<JSONObject> handleConstraintViolationException(ConstraintViolationException exception) {
        JSONObject response = new JSONObject();
        response.put("error", "incorrect request data");
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping
    public ResponseEntity<List<Billing>> getAllBillings() {
        List<Billing> billings = billingService.getAll();

        return ResponseEntity.ok(billings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Billing> getBillingByID(@PathVariable int id) {
        try {
            Billing billing = billingService.getByID(id);

            return ResponseEntity.ok(billing);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<JSONObject> createBilling(@RequestBody BillingDTO billing) {
        try {
            BillingServiceType billingServiceType = BillingServiceType.valueOf(billing.getBillingServiceType());
            BillingStatus billingStatus = BillingStatus.ACTIVE;
            if (billing.getBillingStatus() != null && !billing.getBillingStatus().isBlank()) {
                billingStatus = BillingStatus.valueOf(billing.getBillingStatus());
            }
            int count = billing.getCount();
            double price = billing.getPrice();
            double totalPrice = count * price;
            int consumer = billing.getConsumer();

            ResponseEntity<JSONObject> response = new RestTemplate().exchange(
                userService + "/users/" + consumer, HttpMethod.GET, null, JSONObject.class
            );

            int id = billingService.create(billingServiceType, billingStatus, count, price, totalPrice, consumer);

            return ResponseEntity.created(URI.create("/billings/" + id)).build();
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

    @PostMapping("/pay")
    public ResponseEntity<JSONObject> updateStatus(@PathVariable int id) {
        try {
            billingService.pay(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException | NullPointerException e) {
            JSONObject response = new JSONObject();
            response.put("error", "incorrect request data");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraft(@PathVariable int id) {
        billingService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
