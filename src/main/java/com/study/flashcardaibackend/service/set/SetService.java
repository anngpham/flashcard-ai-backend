package com.study.flashcardaibackend.service.set;

import com.study.flashcardaibackend.dto.set.SetCreationRequestBodyDTO;
import com.study.flashcardaibackend.dto.set.SetUpdateRequestBodyDTO;
import com.study.flashcardaibackend.model.set.Set;

import java.util.UUID;

public interface SetService {
    boolean isSetBelongToUser(UUID setId, UUID userId);

    Set createSet(SetCreationRequestBodyDTO setCreationBody, UUID userId);

    Set updateSet(UUID setId, SetUpdateRequestBodyDTO setUpdateRequestBody);

    void deleteSet(UUID setId);

}