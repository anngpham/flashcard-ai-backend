package com.study.flashcardaibackend.service.user;

import com.study.flashcardaibackend.constant.ErrorConstants;
import com.study.flashcardaibackend.dao.UserRepository;
import com.study.flashcardaibackend.dto.user.LoginBodyDTO;
import com.study.flashcardaibackend.dto.user.RegisterBodyDTO;
import com.study.flashcardaibackend.entity.user.UserEntity;
import com.study.flashcardaibackend.exception.HttpRuntimeException;
import com.study.flashcardaibackend.model.user.User;
import com.study.flashcardaibackend.util.HashingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(RegisterBodyDTO registerBody) {
        if (userRepository.existsByEmail(registerBody.getEmail())) {
            throw new HttpRuntimeException(HttpStatus.BAD_REQUEST, ErrorConstants.EMAIL_EXISTED);
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(registerBody.getEmail());
        userEntity.setPassword(HashingUtil.hashPassword(registerBody.getPassword()));
        UserEntity registeredUser = userRepository.save(userEntity);
        return User.fromEntity(registeredUser);
    }


    @Override
    public User login(LoginBodyDTO loginBody) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(loginBody.getEmail());
        if (userEntity.isEmpty()) {
            throw new HttpRuntimeException(HttpStatus.UNAUTHORIZED, ErrorConstants.EMAIL_NOT_FOUND);
        }
        if (!HashingUtil.verifyPassword(loginBody.getPassword(), userEntity.get().getPassword())) {
            throw new HttpRuntimeException(HttpStatus.UNAUTHORIZED, ErrorConstants.PASSWORD_WRONG);
        }
        return User.fromEntity(userEntity.get());
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        return userEntity.map(User::fromEntity).orElse(null);
    }
}
