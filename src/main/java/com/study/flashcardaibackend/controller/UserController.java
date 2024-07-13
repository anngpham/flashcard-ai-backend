package com.study.flashcardaibackend.controller;

import com.study.flashcardaibackend.constant.ErrorMessage;
import com.study.flashcardaibackend.dao.UserRepository;
import com.study.flashcardaibackend.dto.user.LoginRequestBodyDTO;
import com.study.flashcardaibackend.dto.user.LoginResponseDTO;
import com.study.flashcardaibackend.dto.user.RegisterRequestBodyDTO;
import com.study.flashcardaibackend.dto.user.RegisterResponseDTO;
import com.study.flashcardaibackend.exception.HttpRuntimeException;
import com.study.flashcardaibackend.service.user.JwtService;
import com.study.flashcardaibackend.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private static final String REGISTER_SUCCESS = "User registered successfully";

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestBodyDTO registrationRequest) {
        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new HttpRuntimeException(HttpStatus.BAD_REQUEST, ErrorMessage.EMAIL_EXISTED);
        }

        userService.register(registrationRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterResponseDTO(REGISTER_SUCCESS, jwtService.generateToken(registrationRequest.getEmail())));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestBodyDTO loginRequest) {

        if (!userRepository.existsByEmail(loginRequest.getEmail())) {
            throw new HttpRuntimeException(HttpStatus.BAD_REQUEST, ErrorMessage.NOT_FOUND_EMAIL);
        }

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            if (authentication.isAuthenticated()) {
                return ResponseEntity.ok()
                        .body(new LoginResponseDTO(jwtService.generateToken(loginRequest.getEmail())));
            }
        } catch (Exception e) {
            throw new HttpRuntimeException(HttpStatus.BAD_REQUEST, ErrorMessage.WRONG_PASSWORD);
        }
        throw new HttpRuntimeException(HttpStatus.BAD_REQUEST, ErrorMessage.WRONG_PASSWORD);
    }
}