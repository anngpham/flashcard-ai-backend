package com.study.flashcardaibackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// Controller to test authentication and authorization
public class HelloController {

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {

        return ResponseEntity.ok("Flashcard AI Backend");
    }

    @GetMapping("/helloStaff")
    public ResponseEntity<String> sayHelloToStaff() {

        return ResponseEntity.ok("Hello Staff");
    }
}
