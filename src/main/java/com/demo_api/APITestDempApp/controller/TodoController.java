package com.demo_api.APITestDempApp.controller;


import com.demo_api.APITestDempApp.entity.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@SuppressWarnings({"unused"})
@RestController
@RequestMapping("/api")
public class TodoController {
    private static final Map<Integer, Todo> database = new HashMap<>();

    @GetMapping("/todo/health-check")
    public ResponseEntity<?> healthCheck() {
        return new ResponseEntity<>("HARE KRISHNA! ", HttpStatus.OK);
    }


    @GetMapping("/todo/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable int id) {
        Todo todo = database.get(id);
        if (todo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @PostMapping("/todo")
    public ResponseEntity<?> createTodo(@RequestBody Todo todo) {
        try {
            database.put(todo.getId(), todo);
            return new ResponseEntity<>(todo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("error while creating the todo", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/todo")
    public ResponseEntity<?> getAllTodo() {
        try {
            return new ResponseEntity<>(database, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("error while getting All TODOs", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/todo/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable int id, @RequestBody Todo todo) {
        try {
            database.put(id, todo);
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("error while updating Todo", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable int id) {
        try {
            Todo remove = database.remove(id);
            return new ResponseEntity<>(remove, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("error while deleting todo", HttpStatus.BAD_REQUEST);
        }
    }
}
