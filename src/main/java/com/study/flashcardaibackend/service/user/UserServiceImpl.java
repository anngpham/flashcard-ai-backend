package com.study.flashcardaibackend.service.user;

import com.study.flashcardaibackend.dao.UserRepository;
import com.study.flashcardaibackend.dto.user.RegisterRequestBodyDTO;
import com.study.flashcardaibackend.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void register(RegisterRequestBodyDTO registrationRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(registrationRequest.getEmail());
        userEntity.setPassword(encoder.encode(registrationRequest.getPassword()));
        userRepository.save(userEntity);
    }
}
