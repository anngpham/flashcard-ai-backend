package com.study.flashcardaibackend.service.answer;

import com.study.flashcardaibackend.dao.AnswerRepository;
import com.study.flashcardaibackend.entity.answer.AnswerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public Optional<AnswerEntity> getAnswerById(UUID answerId) {
        return answerRepository.findById(answerId);
    }

    @Override
    public List<AnswerEntity> getAllAnswersByQuestionId(UUID questionId) {
        return answerRepository.findAllByQuestionId(questionId);
    }

    @Override
    public void saveAll(List<AnswerEntity> list) {
        answerRepository.saveAll(list);
    }
}
