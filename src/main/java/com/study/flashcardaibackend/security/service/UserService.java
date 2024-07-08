package com.study.flashcardaibackend.security.service;

import com.study.flashcardaibackend.dto.RegistrationRequest;
import com.study.flashcardaibackend.entity.User;

public interface UserService {

    void register(RegistrationRequest registrationRequest);
}
