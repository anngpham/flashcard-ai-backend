package com.study.flashcardaibackend.dao;

import com.study.flashcardaibackend.entity.set.SetEntity;
import com.study.flashcardaibackend.model.set.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SetRepository extends JpaRepository<SetEntity, UUID> {

    List<SetEntity> findAllByOwnerIdAndIsDeletedIsFalse(UUID userId);

    Page<SetEntity> findByOwnerIdAndIsDeletedIsFalseAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(UUID userId, String title, String description, Pageable pageable);

    @Query("SELECT new com.study.flashcardaibackend.model.set.Set(s.id, s.title, s.description, s.isDeleted, s.owner.id, s.isPublic, s.createdAt, s.updatedAt)" +
            "FROM SetEntity s WHERE s.owner.id = :userId " +
            "AND s.isDeleted = false " +
            "AND (upper(s.title) LIKE %:search% OR upper(s.description) LIKE %:search%)")
    Page<Set> getListOfSetsByOwnerId(UUID userId, String search, Pageable pageable);
}
