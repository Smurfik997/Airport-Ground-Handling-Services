package com.aircraft_ground_handling.service_users.api;

import com.aircraft_ground_handling.service_users.api.dto.UserDTO;
import com.aircraft_ground_handling.service_users.repo.model.User;
import com.aircraft_ground_handling.service_users.repo.model.UserType;
import com.aircraft_ground_handling.service_users.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<JSONObject> handleConstraintViolationException(ConstraintViolationException exception) {
        JSONObject response = new JSONObject();
        response.put("error", "incorrect request data");
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAll();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable int id) {
        try {
            User user = userService.getByID(id);

            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<JSONObject> createUser(@RequestBody UserDTO user) {
        try {
            UserType userType = UserType.valueOf(user.getUserType());
            String name = user.getName();

            int id = userService.create(userType, name);

            return ResponseEntity.created(URI.create("/users/" + id)).build();
        } catch (IllegalArgumentException | NullPointerException e) {
            JSONObject response = new JSONObject();
            response.put("error", "incorrect request data");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
