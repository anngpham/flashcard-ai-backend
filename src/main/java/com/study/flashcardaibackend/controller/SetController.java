package com.study.flashcardaibackend.controller;

import com.study.flashcardaibackend.constant.ErrorMessage;
import com.study.flashcardaibackend.constant.PathConstants;
import com.study.flashcardaibackend.dto.set.SetCreationRequestBodyDTO;
import com.study.flashcardaibackend.dto.set.SetCreationResponseDTO;
import com.study.flashcardaibackend.dto.set.SetUpdateRequestBodyDTO;
import com.study.flashcardaibackend.exception.HttpRuntimeException;
import com.study.flashcardaibackend.model.set.Set;
import com.study.flashcardaibackend.model.user.UserPrincipal;
import com.study.flashcardaibackend.service.set.SetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(PathConstants.SET)
public class SetController {

    private final SetService setService;

    @Autowired
    public SetController(SetService setService) {
        this.setService = setService;
    }

    @PostMapping
    public ResponseEntity<SetCreationResponseDTO> createSet(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody @Valid SetCreationRequestBodyDTO setCreationBody) {

        Set createdSet = setService.createSet(setCreationBody, userPrincipal.getUser().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new SetCreationResponseDTO(createdSet)
        );
    }

    @PutMapping(PathConstants.SET_ID)
    public ResponseEntity<?> updateSet(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable UUID setId,
            @RequestBody @Valid SetUpdateRequestBodyDTO setUpdateBody) {

        if (!setService.isSetBelongToUser(setId, userPrincipal.getUser().getId())) {
            throw new HttpRuntimeException(HttpStatus.FORBIDDEN, ErrorMessage.SET_NOT_BELONG_TO_USER);
        }

        Set updatedSet = setService.updateSet(setId, setUpdateBody);
        return ResponseEntity.status(HttpStatus.OK).body(updatedSet);
    }

    @DeleteMapping(PathConstants.SET_ID)
    public ResponseEntity<?> deleteSet(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable UUID setId) {

        if (!setService.isSetBelongToUser(setId, userPrincipal.getUser().getId())) {
            throw new HttpRuntimeException(HttpStatus.FORBIDDEN, ErrorMessage.SET_NOT_BELONG_TO_USER);
        }

        setService.deleteSet(setId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
