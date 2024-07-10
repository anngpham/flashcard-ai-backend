package com.study.flashcardaibackend.controller;

import com.study.flashcardaibackend.dto.QuestionRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private SetService setService;

    @Autowired
    private QuestionService questionService;

    // create a question and add it to the set
    @PostMapping
    public ResponseEntity<?> createQuestion(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody @Valid QuestionRequest questionRequest) {

        if (!questionRequest.isValidQuestion())
            return ResponseEntity.badRequest().body("invalid question");

        Question createdQuestion;

        try {
            Set set = setService.getSet(userPrincipal.getUser(), questionRequest.getSetId());
            createdQuestion = questionService.createQuestion(set, questionRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    }
}
