package com.study.flashcardaibackend.service.user;

import com.study.flashcardaibackend.constant.ErrorMessage;
import com.study.flashcardaibackend.dao.UserRepository;
import com.study.flashcardaibackend.entity.user.UserEntity;
import com.study.flashcardaibackend.model.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(ErrorMessage.USER_NOT_FOUND);
        }

        return new UserPrincipal(user);

    }
}
