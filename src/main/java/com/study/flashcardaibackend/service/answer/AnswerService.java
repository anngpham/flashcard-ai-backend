package com.study.flashcardaibackend.service.answer;

import com.study.flashcardaibackend.entity.answer.AnswerEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnswerService {
    Optional<AnswerEntity> getAnswerById(UUID answerId);

    List<AnswerEntity> getAllAnswersByQuestionId(UUID questionId);

    void saveAll(List<AnswerEntity> list);
}
