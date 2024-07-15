package com.study.flashcardaibackend.controller;

import com.study.flashcardaibackend.constant.PathConstants;
import com.study.flashcardaibackend.dto.user.LoginBodyDTO;
import com.study.flashcardaibackend.dto.user.LoginResponseDTO;
import com.study.flashcardaibackend.dto.user.RegisterBodyDTO;
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
@RequestMapping(PathConstants.AUTH)
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping(PathConstants.AUTH_REGISTER)
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterBodyDTO registerBody) {
        User registeredUser = userService.register(registerBody);
        String email = registeredUser.getEmail();
        String token = jwtService.generateToken(email);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponseDTO(token, email));
    }

    @PostMapping(PathConstants.AUTH_LOGIN)
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginBodyDTO loginBody) {
        User loggedInUser = userService.login(loginBody);
        String token = jwtService.generateToken(loggedInUser.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponseDTO(loggedInUser, token));
    }
}