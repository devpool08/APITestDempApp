package com.demo_api.APITestDempApp.controller;

import com.demo_api.APITestDempApp.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@SuppressWarnings({"unused"})
public class UserController {
    private static final Map<Integer, User> database = new HashMap<>();
    private static int countId = 0;

    @RequestMapping("/hello")
    public String helloWorld() {
        return "Hare Krishna !";
    }

    @PostMapping("/user")
    public ResponseEntity<?> saveData(@RequestBody User user) {
        database.put(countId++, user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<>(database.values(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        User user = database.get(id);
        if (user == null) return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User user) {
        User existingUser = database.get(id);
        if (existingUser == null) return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        database.put(id, user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        User user = database.remove(id);
        if (user == null) return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>("User deleted", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/clear")
    public ResponseEntity<?> clearDB() {
        database.clear();
        countId = 0;
        return new ResponseEntity<>("Database cleared", HttpStatus.NO_CONTENT);
    }
}
