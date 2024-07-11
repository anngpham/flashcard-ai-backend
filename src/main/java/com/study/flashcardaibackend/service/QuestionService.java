package com.study.flashcardaibackend.service;

import com.study.flashcardaibackend.dto.QuestionCreationRequest;
import com.study.flashcardaibackend.dto.QuestionUpdateRequest;
import com.study.flashcardaibackend.entity.Question;
import com.study.flashcardaibackend.entity.Set;

import java.util.UUID;

public interface QuestionService {


    Question createQuestion(Set set, QuestionCreationRequest questionCreationRequest);

    Question updateQuestion(QuestionUpdateRequest questionUpdateRequest, UUID questionId);

    void deleteQuestion(UUID questionId);
}
