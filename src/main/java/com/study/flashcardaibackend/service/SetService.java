package com.study.flashcardaibackend.service;

import com.study.flashcardaibackend.dto.SetRequest;
import com.study.flashcardaibackend.entity.Set;
import com.study.flashcardaibackend.entity.User;

import java.util.UUID;

public interface SetService {
    Set createSet(User user, SetRequest setRequest);

    Set getSet(User user, UUID setId);

    Set updateSet(User user, SetRequest setRequest, UUID setId);

    void deleteSet(User user, UUID setId);

    boolean isUserAccessibleWithSet(User user, UUID setId);
}
