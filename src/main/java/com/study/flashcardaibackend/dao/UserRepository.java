package com.study.flashcardaibackend.dao;

import com.study.flashcardaibackend.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface UserRepository extends JpaRepository<UserEntity, UUID>{

    UserEntity findByEmail(String email);

    boolean existsByEmail(String email);
}
