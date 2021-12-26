package com.aircraft_ground_handling.service_users.repo;

import com.aircraft_ground_handling.service_users.repo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
}
