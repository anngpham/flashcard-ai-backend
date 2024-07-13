package com.study.flashcardaibackend.dao;

import com.study.flashcardaibackend.entity.answer.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AnswerRepository extends JpaRepository<AnswerEntity, UUID> {

    List<AnswerEntity> findAllByQuestionIdAndIsDeleted(UUID questionID, boolean idDeleted);

    @Query("SELECT COUNT(a) FROM AnswerEntity a WHERE a.question.id = :questionID AND a.isCorrect = true AND a.isDeleted = false")
    int numberOfCorrectAnswers(UUID questionID);
}
