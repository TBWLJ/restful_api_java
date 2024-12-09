package com.postgraduate.manifestation.controller;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final MongoTemplate mongoTemplate;

    public HelloController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello world";
    }

    @GetMapping("/test-db")
    public ResponseEntity<String> testDB() {
        try {
            mongoTemplate.getDb().getName();
            return ResponseEntity.ok("Connected to MongoDB!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to connect: " + e.getMessage());
        }
    }
} 