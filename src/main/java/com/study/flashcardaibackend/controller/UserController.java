package com.study.flashcardaibackend.controller;

import com.study.flashcardaibackend.dto.user.LoginRequestBodyDTO;
import com.study.flashcardaibackend.dto.user.LoginResponseDTO;
import com.study.flashcardaibackend.dto.user.RegisterRequestBodyDTO;
import com.study.flashcardaibackend.dto.user.RegisterResponseDTO;
import com.study.flashcardaibackend.model.user.User;
import com.study.flashcardaibackend.service.user.JwtService;
import com.study.flashcardaibackend.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestBodyDTO registerRequestBody) {
        User registeredUser = userService.register(registerRequestBody);
        String email = registeredUser.getEmail();
        String token = jwtService.generateToken(email);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponseDTO(token, email));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestBodyDTO loginRequestBody) {
        User loggedInUser = userService.login(loginRequestBody);
        String token = jwtService.generateToken(loggedInUser.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponseDTO(loggedInUser, token));
    }
}