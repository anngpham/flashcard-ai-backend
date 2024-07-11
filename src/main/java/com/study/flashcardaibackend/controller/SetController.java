package com.study.flashcardaibackend.controller;

import com.study.flashcardaibackend.dto.SetRequest;
import com.study.flashcardaibackend.entity.Set;
import com.study.flashcardaibackend.entity.UserPrincipal;
import com.study.flashcardaibackend.service.SetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/set")
public class SetController {

    private SetService setService;

    @Autowired
    public SetController(SetService setService) {
        this.setService = setService;
    }

    @PostMapping
    public ResponseEntity<Set> createSet(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody @Valid SetRequest setRequest) {

        Set createdSet = setService.createSet(userPrincipal.getUser(), setRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSet);
    }

    @PutMapping("/{setId}")
    public ResponseEntity<Set> updateSet(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable UUID setId,
            @RequestBody @Valid SetRequest setRequest) {

        Set updatedSet;

        try {
            updatedSet = setService.updateSet(userPrincipal.getUser(), setRequest, setId);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(updatedSet);
    }

    @DeleteMapping("/{setId}")
    public ResponseEntity<String> deleteSet(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable UUID setId) {

        try {
            setService.deleteSet(userPrincipal.getUser(), setId);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body("Set deleted");
    }

}
