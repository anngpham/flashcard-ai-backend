package com.study.flashcardaibackend.service.question;

import com.study.flashcardaibackend.dto.question.QuestionCreationRequestDTO;
import com.study.flashcardaibackend.dto.question.QuestionUpdateRequestDTO;
import com.study.flashcardaibackend.model.question.Question;

import java.util.UUID;

public interface QuestionService {


    Question createQuestion(QuestionCreationRequestDTO questionCreationRequest, UUID setId);

    Question updateQuestion(QuestionUpdateRequestDTO questionUpdateRequest, UUID questionId);

    void deleteQuestion(UUID questionId);

    Question getQuestion(UUID questionId);
}
