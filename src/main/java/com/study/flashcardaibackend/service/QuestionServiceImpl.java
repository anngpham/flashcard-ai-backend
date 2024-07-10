package com.study.flashcardaibackend.service;

import com.study.flashcardaibackend.dao.AnswerRepository;
import com.study.flashcardaibackend.dao.QuestionRepository;
import com.study.flashcardaibackend.dto.AnswerRequest;
import com.study.flashcardaibackend.dto.QuestionRequest;
import com.study.flashcardaibackend.entity.Answer;
import com.study.flashcardaibackend.entity.Question;
import com.study.flashcardaibackend.entity.QuestionType;
import com.study.flashcardaibackend.entity.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Override
    public Question createQuestion(Set set, QuestionRequest questionRequest) {
        Question question = new Question();
        question.setTitle(questionRequest.getTitle());
        question.setQuestionType(QuestionType.valueOf(questionRequest.getQuestionType()));

        for (AnswerRequest answerRequest : questionRequest.getAnswers()){
            Answer answer = new Answer();
            answer.setContent(answerRequest.getContent());
            answer.setCorrect(answerRequest.isCorrect());
            answer.setQuestion(question);

            question.addAnswer(answer);
        }

        question.setSet(set);


        return questionRepository.save(question);

    }
}
