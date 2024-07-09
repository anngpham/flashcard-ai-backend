package com.study.flashcardaibackend.controller;

import com.study.flashcardaibackend.dto.SetCreationRequest;
import com.study.flashcardaibackend.entity.UserPrincipal;
import com.study.flashcardaibackend.service.SetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/set")
public class SetController {

    private SetService setService;

    @Autowired
    public SetController(SetService setService) {
        this.setService = setService;
    }

    @PostMapping
    public ResponseEntity<String> createSet(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody @Valid SetCreationRequest setCreationRequest) {

        setService.addSet(userPrincipal.getUser(), setCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Set created");
    }
}
