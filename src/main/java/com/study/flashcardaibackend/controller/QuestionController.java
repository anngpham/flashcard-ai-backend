package com.study.flashcardaibackend.controller;

import com.study.flashcardaibackend.constant.PathConstants;
import com.study.flashcardaibackend.dto.common.SuccessResponseDTO;
import com.study.flashcardaibackend.dto.question.QuestionCreationBodyDTO;
import com.study.flashcardaibackend.dto.question.QuestionUpdateBodyDTO;
import com.study.flashcardaibackend.model.question.Question;
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
    public ResponseEntity<SuccessResponseDTO<Question>> createQuestion(
            @PathVariable UUID setId,
            @RequestBody @Valid QuestionCreationBodyDTO questionCreationBody) {
        Question createdQuestion = questionService.createQuestion(questionCreationBody, setId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponseDTO<Question>(createdQuestion));
    }

    @PutMapping(PathConstants.QUESTION_ID)
    public ResponseEntity<Question> updateQuestion(
            @PathVariable UUID setId,
            @PathVariable UUID questionId,
            @RequestBody @Valid QuestionUpdateBodyDTO questionUpdateBody) {
        Question updatedQuestion = questionService.updateQuestion(questionUpdateBody, questionId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedQuestion);

    }

    @DeleteMapping(PathConstants.QUESTION_ID)
    public ResponseEntity<Void> deleteQuestion(@PathVariable UUID setId, @PathVariable UUID questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
