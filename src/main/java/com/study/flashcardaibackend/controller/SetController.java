package com.study.flashcardaibackend.controller;

import com.study.flashcardaibackend.constant.FilterAttrConstants;
import com.study.flashcardaibackend.constant.PathConstants;
import com.study.flashcardaibackend.dto.set.SetCreationRequestBodyDTO;
import com.study.flashcardaibackend.dto.set.SetCreationResponseDTO;
import com.study.flashcardaibackend.dto.set.SetUpdateRequestBodyDTO;
import com.study.flashcardaibackend.model.set.Set;
import com.study.flashcardaibackend.service.set.SetService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            HttpServletRequest request,
            @RequestBody @Valid SetCreationRequestBodyDTO setCreationBody) {
        UUID userId = (UUID) request.getAttribute(FilterAttrConstants.USER_ID);
        Set createdSet = setService.createSet(setCreationBody, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SetCreationResponseDTO(createdSet));
    }

    @PutMapping(PathConstants.SET_ID)
    public ResponseEntity<?> updateSet(
            @PathVariable UUID setId,
            @RequestBody @Valid SetUpdateRequestBodyDTO setUpdateBody) {
        Set updatedSet = setService.updateSet(setId, setUpdateBody);
        return ResponseEntity.status(HttpStatus.OK).body(updatedSet);
    }

    @DeleteMapping(PathConstants.SET_ID)
    public ResponseEntity<?> deleteSet(@PathVariable UUID setId) {
        setService.deleteSet(setId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
