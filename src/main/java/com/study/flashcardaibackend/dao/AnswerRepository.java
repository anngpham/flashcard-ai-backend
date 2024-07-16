package com.study.flashcardaibackend.dao;

import com.study.flashcardaibackend.entity.answer.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AnswerRepository extends JpaRepository<AnswerEntity, UUID> {

    List<AnswerEntity> findAllByQuestionId(UUID questionId);
}
