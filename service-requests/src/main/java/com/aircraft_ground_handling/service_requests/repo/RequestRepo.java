package com.aircraft_ground_handling.service_requests.repo;

import com.aircraft_ground_handling.service_requests.repo.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepo extends JpaRepository<Request, Integer> {
}
