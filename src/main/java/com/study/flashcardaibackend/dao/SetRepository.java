package com.study.flashcardaibackend.dao;

import com.study.flashcardaibackend.entity.set.SetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SetRepository extends JpaRepository<SetEntity, UUID> {

    boolean existsByIdAndOwnerId(UUID setId, UUID ownerId);
}
