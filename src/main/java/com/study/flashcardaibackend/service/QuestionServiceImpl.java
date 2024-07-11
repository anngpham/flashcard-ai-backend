package com.study.flashcardaibackend.service;

import com.study.flashcardaibackend.dao.AnswerRepository;
import com.study.flashcardaibackend.dao.QuestionRepository;
import com.study.flashcardaibackend.dto.*;
import com.study.flashcardaibackend.entity.Answer;
import com.study.flashcardaibackend.entity.Question;
import com.study.flashcardaibackend.entity.QuestionType;
import com.study.flashcardaibackend.entity.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Override
    public Question createQuestion(Set set, QuestionCreationRequest questionCreationRequest) {
        Question question = new Question();

        question.setTitle(questionCreationRequest.getTitle());
        question.setQuestionType(QuestionType.valueOf(questionCreationRequest.getQuestionType()));

        for (AnswerCreationRequest answerCreationRequest : questionCreationRequest.getNewAnswers()){
            Answer answer = new Answer();
            answer.setContent(answerCreationRequest.getContent());
            answer.setCorrect(answerCreationRequest.isCorrect());
            answer.setQuestion(question);

            question.addAnswer(answer);
        }

        question.setSet(set);

        return questionRepository.save(question);

    }

    @Override
    public Question updateQuestion(QuestionUpdateRequest questionUpdateRequest, UUID questionId) {

        System.out.println(questionUpdateRequest);

        Question question = questionRepository.findById(questionId).orElseThrow(() -> new RuntimeException("Question not found"));

        question.setTitle(questionUpdateRequest.getTitle());
        question.setQuestionType(QuestionType.valueOf(questionUpdateRequest.getQuestionType()));

        if (questionUpdateRequest.getNewAnswers() != null) {
            for (AnswerCreationRequest newAnswer : questionUpdateRequest.getNewAnswers()) {
                Answer answer = new Answer();
                answer.setContent(newAnswer.getContent());
                answer.setCorrect(newAnswer.isCorrect());
                answer.setQuestion(question);

                answerRepository.save(answer);
            }
        }

        if (questionUpdateRequest.getDeleteAnswers() != null) {
            for (UUID id : questionUpdateRequest.getDeleteAnswers()) {
                Answer answer = answerRepository.findById(id).orElseThrow(() -> new RuntimeException("Answer not found"));
                answer.setDeleted(true);
                answerRepository.save(answer);
            }
        }

        if (questionUpdateRequest.getUpdateAnswers() != null) {
            for (AnswerUpdateRequest updateAnswer : questionUpdateRequest.getUpdateAnswers()) {
                Answer answer = answerRepository.findById(updateAnswer.getId()).orElseThrow(() -> new RuntimeException("Answer not found"));
                if (updateAnswer.getContent() != null)
                    answer.setContent(updateAnswer.getContent());
                if (updateAnswer.getIsCorrect() != null)
                    answer.setCorrect(updateAnswer.getIsCorrect());
            }
        }
        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(UUID questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new RuntimeException("Question not found"));
        List<Answer> answers = question.getAnswers();
        answers.forEach(answer -> answer.setDeleted(true));
        question.setDeleted(true);

        questionRepository.save(question);

    }


}
