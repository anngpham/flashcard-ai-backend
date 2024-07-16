package com.study.flashcardaibackend.service.set;

import com.study.flashcardaibackend.dto.set.SetCreationBodyDTO;
import com.study.flashcardaibackend.dto.set.SetUpdateBodyDTO;
import com.study.flashcardaibackend.model.set.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SetService {
    Set getSet(UUID setId);

    Set createSet(SetCreationBodyDTO setCreationBody, UUID userId);

    Set updateSet(UUID setId, SetUpdateBodyDTO setUpdateBody);

    void deleteSet(UUID setId);

    Page<Set> getListOfSetByOwnerId(UUID userId, String search, Pageable pageable);
}