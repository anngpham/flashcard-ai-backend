package com.study.flashcardaibackend.security.service;

import com.study.flashcardaibackend.dao.UserRepository;
import com.study.flashcardaibackend.entity.UserPrincipal;
import com.study.flashcardaibackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{


        @Autowired
        private UserRepository userRepository;


        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

            User user = userRepository.findByEmail(email);
            if (user == null) {
                System.out.println("User 404");
                throw new UsernameNotFoundException("User 404");
            }

            return new UserPrincipal(user);

        }
}
