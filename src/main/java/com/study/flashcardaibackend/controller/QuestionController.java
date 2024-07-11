package com.study.flashcardaibackend.controller;

import com.study.flashcardaibackend.dto.QuestionCreationRequest;
import com.study.flashcardaibackend.dto.QuestionUpdateRequest;
import com.study.flashcardaibackend.entity.Question;
import com.study.flashcardaibackend.entity.Set;
import com.study.flashcardaibackend.entity.UserPrincipal;
import com.study.flashcardaibackend.service.QuestionService;
import com.study.flashcardaibackend.service.SetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("set/{setId}/question")
public class QuestionController {

    @Autowired
    private SetService setService;

    @Autowired
    private QuestionService questionService;

    @PostMapping
    public ResponseEntity<?> createQuestion(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable UUID setId,
            @RequestBody @Valid QuestionCreationRequest questionCreationRequest) {

        if (!questionCreationRequest.isValid())
            return ResponseEntity.badRequest().body("invalid question");

        Question createdQuestion;

        try {
            Set set = setService.getSet(userPrincipal.getUser(), setId);
            createdQuestion = questionService.createQuestion(set, questionCreationRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<?> updateQuestion(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable UUID questionId,
            @PathVariable UUID setId,
            @RequestBody @Valid QuestionUpdateRequest questionUpdateRequest) {


        if (!setService.isUserAccessibleWithSet(userPrincipal.getUser(), setId))
            return ResponseEntity.notFound().build();

        Question updatedQuestion;

        try {
            updatedQuestion = questionService.updateQuestion(questionUpdateRequest, questionId);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(updatedQuestion);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<?> deleteQuestion(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable UUID questionId,
            @PathVariable UUID setId) {

        if (!setService.isUserAccessibleWithSet(userPrincipal.getUser(), setId))
            return ResponseEntity.notFound().build();

        try {
            questionService.deleteQuestion(questionId);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body("Question deleted");

    }

}
