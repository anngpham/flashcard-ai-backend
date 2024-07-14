package com.study.flashcardaibackend.controller;

import com.study.flashcardaibackend.dto.common.SuccessResponseDTO;
import com.study.flashcardaibackend.dto.question.QuestionCreationRequestDTO;
import com.study.flashcardaibackend.dto.question.QuestionUpdateRequestDTO;
import com.study.flashcardaibackend.model.question.Question;
import com.study.flashcardaibackend.service.question.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            @PathVariable UUID setId,
            @RequestBody @Valid QuestionCreationRequestDTO questionCreationRequest) {
        Question createdQuestion = questionService.createQuestion(questionCreationRequest, setId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponseDTO<Question>(createdQuestion));
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<?> updateQuestion(
            @PathVariable UUID setId,
            @PathVariable UUID questionId,
            @RequestBody @Valid QuestionUpdateRequestDTO questionUpdateBody) {
        Question updatedQuestion = questionService.updateQuestion(questionUpdateBody, questionId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedQuestion);

    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable UUID setId, @PathVariable UUID questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
