package com.aircraft_ground_handling.service_billings.repo;

import com.aircraft_ground_handling.service_billings.repo.model.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepo extends JpaRepository<Billing, Integer> {
}
