package com.aircraft_ground_handling.service_billings.service;

import com.aircraft_ground_handling.service_billings.repo.BillingRepo;
import com.aircraft_ground_handling.service_billings.repo.model.Billing;
import com.aircraft_ground_handling.service_billings.repo.model.BillingServiceType;
import com.aircraft_ground_handling.service_billings.repo.model.BillingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BillingService {
    public final BillingRepo billingRepo;

    public List<Billing> getAll() {
        List<Billing> billings = billingRepo.findAll();
        return billings;
    }

    public Billing getByID(int id) throws IllegalArgumentException {
        Optional<Billing> billing = billingRepo.findById(id);

        if (billing.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            return billing.get();
        }
    }

    public int create(BillingServiceType billingServiceType, BillingStatus billingStatus, int count,
                      double price, double totalPrice, int consumer) throws IllegalArgumentException {
        Billing billing = new Billing(billingServiceType, billingStatus, count, price, totalPrice, consumer);
        Billing savedBilling = billingRepo.save(billing);

        return savedBilling.getId();
    }

    public void pay(int id) throws IllegalArgumentException {
        Billing billing = getByID(id);
        billing.setBillingStatus(BillingStatus.CLOSED);
        billingRepo.save(billing);
    }

    public void delete(int id) {
        try {
            billingRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return;
        }
    }
}
