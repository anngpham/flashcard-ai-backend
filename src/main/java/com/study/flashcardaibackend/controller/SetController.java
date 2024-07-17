package com.study.flashcardaibackend.controller;

import com.study.flashcardaibackend.constant.FilterAttrConstants;
import com.study.flashcardaibackend.constant.PathConstants;
import com.study.flashcardaibackend.dto.set.SetCreationBodyDTO;
import com.study.flashcardaibackend.dto.set.SetCreationResponseDTO;
import com.study.flashcardaibackend.dto.set.SetUpdateBodyDTO;
import com.study.flashcardaibackend.dto.set.SetUpdateResponseDTO;
import com.study.flashcardaibackend.model.set.Set;
import com.study.flashcardaibackend.service.set.SetService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(PathConstants.SET)
@Validated
public class SetController {

    private final SetService setService;

    @Autowired
    public SetController(SetService setService) {
        this.setService = setService;
    }

    @PostMapping
    public ResponseEntity<SetCreationResponseDTO> createSet(
            HttpServletRequest request,
            @RequestBody @Valid SetCreationBodyDTO setCreationBody) {
        UUID userId = (UUID) request.getAttribute(FilterAttrConstants.USER_ID);
        Set createdSet = setService.createSet(setCreationBody, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SetCreationResponseDTO(createdSet));
    }

    @PutMapping(PathConstants.SET_ID)
    public ResponseEntity<SetUpdateResponseDTO> updateSet(
            @PathVariable UUID setId,
            @RequestBody @Valid SetUpdateBodyDTO setUpdateBody) {
        Set updatedSet = setService.updateSet(setId, setUpdateBody);
        return ResponseEntity.status(HttpStatus.OK).body(new SetUpdateResponseDTO(updatedSet));
    }

    @DeleteMapping(PathConstants.SET_ID)
    public ResponseEntity<Void> deleteSet(@PathVariable UUID setId) {
        setService.deleteSet(setId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<Page<Set>> getListOfSet(
            HttpServletRequest request,
            @RequestParam(value = "page", defaultValue = "1", required = false) @Min(1) int page,
            @RequestParam(value = "pageSize", defaultValue = "20") @Min(1) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "updatedAt", required = false) @Pattern(regexp = "updatedAt|createdAt") String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "desc", required = false) @Pattern(regexp = "asc|desc") String sortDirection,
            @RequestParam(value = "search", defaultValue = "", required = false) @Size(max = 10) String search

    ) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);

        UUID userId = (UUID) request.getAttribute(FilterAttrConstants.USER_ID);
        Page<Set> sets = setService.getListOfSetByOwnerId(userId, search, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(sets);
    }
}
