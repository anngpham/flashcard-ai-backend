package com.study.flashcardaibackend.service;

import com.study.flashcardaibackend.dto.SetCreationRequest;
import com.study.flashcardaibackend.entity.User;

public interface SetService {
    void addSet(User user, SetCreationRequest setCreationRequest);
}
