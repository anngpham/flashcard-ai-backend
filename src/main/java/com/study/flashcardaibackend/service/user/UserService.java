package com.study.flashcardaibackend.service.user;

import com.study.flashcardaibackend.dto.user.LoginBodyDTO;
import com.study.flashcardaibackend.dto.user.RegisterBodyDTO;
import com.study.flashcardaibackend.model.user.User;

public interface UserService {

    User register(RegisterBodyDTO registerBody);

    User login(LoginBodyDTO loginBody);

    User getUserByEmail(String email);
}
