package com.study.flashcardaibackend.service.user;

import com.study.flashcardaibackend.dto.user.LoginRequestBodyDTO;
import com.study.flashcardaibackend.dto.user.RegisterRequestBodyDTO;
import com.study.flashcardaibackend.model.user.User;

public interface UserService {

    User register(RegisterRequestBodyDTO registrationRequest);

    User login(LoginRequestBodyDTO loginRequest);

    User getUserByEmail(String email);
}
