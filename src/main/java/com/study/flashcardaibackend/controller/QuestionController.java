package com.study.flashcardaibackend.controller;

import com.study.flashcardaibackend.dto.question.QuestionCreationRequestDTO;
import com.study.flashcardaibackend.dto.question.QuestionUpdateRequestDTO;
import com.study.flashcardaibackend.model.question.Question;
import com.study.flashcardaibackend.model.user.UserPrincipal;
import com.study.flashcardaibackend.service.question.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/sets/{setId}/questions")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<?> createQuestion(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable UUID setId,
            @RequestBody @Valid QuestionCreationRequestDTO questionCreationRequest) {
        Question createdQuestionEntity = questionService.createQuestion(setId, questionCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestionEntity);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<?> updateQuestion(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable UUID questionId,
            @PathVariable UUID setId,
            @RequestBody @Valid QuestionUpdateRequestDTO questionUpdateRequest) {

        Question updatedQuestion = questionService.updateQuestion(questionUpdateRequest, questionId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedQuestion);

    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<?> deleteQuestion(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable UUID questionId,
            @PathVariable UUID setId) {

        questionService.deleteQuestion(questionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
