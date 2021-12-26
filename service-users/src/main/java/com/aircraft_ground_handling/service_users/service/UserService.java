package com.aircraft_ground_handling.service_users.service;

import com.aircraft_ground_handling.service_users.repo.UserRepo;
import com.aircraft_ground_handling.service_users.repo.model.User;
import com.aircraft_ground_handling.service_users.repo.model.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class UserService {
    public final UserRepo userRepo;

    public List<User> getAll() {
        List<User> users = userRepo.findAll();
        return users;
    }

    public User getByID(int id) throws IllegalArgumentException {
        Optional<User> user = userRepo.findById(id);

        if (user.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            return user.get();
        }
    }

    public int create(UserType userType, String name) throws IllegalArgumentException {
        User user = new User(userType, name);
        User savedUser = userRepo.save(user);

        return savedUser.getId();
    }

    public void delete(int id) {
        try {
            userRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return;
        }
    }
}
