package com.study.flashcardaibackend.service.user;

import com.study.flashcardaibackend.dto.user.RegisterRequestBodyDTO;

public interface UserService {

    void register(RegisterRequestBodyDTO registrationRequest);
}
