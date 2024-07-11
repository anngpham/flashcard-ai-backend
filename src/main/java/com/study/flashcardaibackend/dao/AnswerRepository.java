package com.study.flashcardaibackend.dao;

import com.study.flashcardaibackend.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnswerRepository extends JpaRepository<Answer, UUID> {
}
