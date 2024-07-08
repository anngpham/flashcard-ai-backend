package com.study.flashcardaibackend.dao;

import com.study.flashcardaibackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID>{
//    User findByUsername(String username);


    User findByEmail(String email);
}
