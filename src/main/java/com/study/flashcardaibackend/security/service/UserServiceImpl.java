package com.study.flashcardaibackend.security.service;

import com.study.flashcardaibackend.dao.UserRepository;
import com.study.flashcardaibackend.dto.RegistrationRequest;
import com.study.flashcardaibackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void register(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(encoder.encode(registrationRequest.getPassword()));
        userRepository.save(user);
    }
}
