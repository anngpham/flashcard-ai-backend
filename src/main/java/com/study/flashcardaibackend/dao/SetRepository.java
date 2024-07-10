package com.study.flashcardaibackend.dao;

import com.study.flashcardaibackend.entity.Set;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SetRepository extends JpaRepository<Set, UUID> {

}
