package com.study.flashcardaibackend.service.question;

import com.study.flashcardaibackend.dto.question.QuestionCreationBodyDTO;
import com.study.flashcardaibackend.dto.question.QuestionUpdateBodyDTO;
import com.study.flashcardaibackend.model.question.Question;

import java.util.UUID;

public interface QuestionService {


    Question createQuestion(QuestionCreationBodyDTO questionCreationBody, UUID setId);

    Question updateQuestion(QuestionUpdateBodyDTO questionUpdateBody, UUID questionId);

    void deleteQuestion(UUID questionId);

    Question getQuestion(UUID questionId);
}
