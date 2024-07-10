package com.study.flashcardaibackend.service;

import com.study.flashcardaibackend.dto.QuestionRequest;
import com.study.flashcardaibackend.entity.Question;
import com.study.flashcardaibackend.entity.Set;

public interface QuestionService {


    Question createQuestion(Set set, QuestionRequest questionRequest);
}
