package com.study.flashcardaibackend.controller;

import com.study.flashcardaibackend.constant.PathConstants;
import com.study.flashcardaibackend.dto.question.QuestionCreationBodyDTO;
import com.study.flashcardaibackend.dto.question.QuestionCreationResponse;
import com.study.flashcardaibackend.dto.question.QuestionUpdateBodyDTO;
import com.study.flashcardaibackend.dto.question.QuestionUpdateResponse;
import com.study.flashcardaibackend.model.question.QuestionDetail;
import com.study.flashcardaibackend.service.question.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(PathConstants.SET + PathConstants.SET_ID + PathConstants.QUESTION)
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<QuestionCreationResponse> createQuestion(
            @PathVariable UUID setId,
            @RequestBody @Valid QuestionCreationBodyDTO questionCreationBody) {
        QuestionDetail createdQuestion = questionService.createQuestion(questionCreationBody, setId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new QuestionCreationResponse(createdQuestion));
    }

    @PutMapping(PathConstants.QUESTION_ID)
    public ResponseEntity<QuestionUpdateResponse> updateQuestion(
            @PathVariable UUID setId,
            @PathVariable UUID questionId,
            @RequestBody @Valid QuestionUpdateBodyDTO questionUpdateBody) {
        QuestionDetail updatedQuestion = questionService.updateQuestion(questionUpdateBody, questionId);
        return ResponseEntity.status(HttpStatus.OK).body(new QuestionUpdateResponse(updatedQuestion));

    }

    @DeleteMapping(PathConstants.QUESTION_ID)
    public ResponseEntity<Void> deleteQuestion(@PathVariable UUID setId, @PathVariable UUID questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
