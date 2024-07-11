package com.study.flashcardaibackend.dao;

import com.study.flashcardaibackend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
}
