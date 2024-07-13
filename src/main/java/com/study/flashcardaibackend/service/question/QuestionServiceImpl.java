package com.study.flashcardaibackend.service.question;

import com.study.flashcardaibackend.constant.ErrorMessage;
import com.study.flashcardaibackend.dao.AnswerRepository;
import com.study.flashcardaibackend.dao.QuestionRepository;
import com.study.flashcardaibackend.dao.SetRepository;
import com.study.flashcardaibackend.dto.answer.AnswerCreationRequestDTO;
import com.study.flashcardaibackend.dto.answer.AnswerUpdateRequestDTO;
import com.study.flashcardaibackend.dto.question.QuestionCreationRequestDTO;
import com.study.flashcardaibackend.dto.question.QuestionUpdateRequestDTO;
import com.study.flashcardaibackend.entity.answer.AnswerEntity;
import com.study.flashcardaibackend.entity.question.QuestionEntity;
import com.study.flashcardaibackend.enums.QuestionType;
import com.study.flashcardaibackend.exception.HttpRuntimeException;
import com.study.flashcardaibackend.model.common.CommonList;
import com.study.flashcardaibackend.model.question.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
public class QuestionServiceImpl implements QuestionService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final SetRepository setRepository;

    @Autowired
    public QuestionServiceImpl(AnswerRepository answerRepository, QuestionRepository questionRepository, SetRepository setRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.setRepository = setRepository;
    }

    @Override
    @Transactional
    public Question createQuestion(UUID setId, QuestionCreationRequestDTO questionCreationRequest) {
        if (!setRepository.existsById(setId)) {
            throw new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorMessage.SET_NOT_FOUND);
        }

        QuestionEntity question = new QuestionEntity();
        question.setSet(setRepository.getReferenceById(setId));
        question.setTitle(questionCreationRequest.getTitle());
        question.setQuestionType(QuestionType.valueOf(questionCreationRequest.getQuestionType()));
        question.setAnswers(questionCreationRequest.getNewAnswers().stream().map(a -> {
            AnswerEntity answer = new AnswerEntity();
            answer.setContent(a.getContent());
            answer.setCorrect(a.getIsCorrect());
            answer.setQuestion(question);
            return answer;
        }).toList());

        QuestionEntity createdQuestion = questionRepository.save(question);
        return Question.fromEntity(createdQuestion);

    }

    @Override
    @Transactional
    public Question updateQuestion(QuestionUpdateRequestDTO questionUpdateRequest, UUID questionId) {

        QuestionEntity question = questionRepository.findById(questionId).orElseThrow(()
                -> new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorMessage.NOT_FOUND_QUESTION));

        question.setTitle(questionUpdateRequest.getTitle());
        question.setQuestionType(QuestionType.valueOf(questionUpdateRequest.getQuestionType()));

        CommonList<AnswerEntity> list = new CommonList<>();

        if (questionUpdateRequest.getNewAnswers() != null) {
            for (AnswerCreationRequestDTO newAnswer : questionUpdateRequest.getNewAnswers()) {
                AnswerEntity answer = new AnswerEntity();
                answer.setContent(newAnswer.getContent());
                answer.setCorrect(newAnswer.getIsCorrect());
                answer.setQuestion(question);
                list.add(answer);
            }
        }


        if (questionUpdateRequest.getDeleteAnswers() != null) {
            for (UUID id : questionUpdateRequest.getDeleteAnswers()) {
                AnswerEntity answer = answerRepository.findById(id).orElseThrow(()
                        -> new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorMessage.NOT_FOUND_ANSWER));
                answer.setDeleted(true);
                list.addIfNotExisted(answer, new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorMessage.DUPLICATE_ANSWER_ID));
            }
        }

        if (questionUpdateRequest.getUpdateAnswers() != null) {
            for (AnswerUpdateRequestDTO updateAnswer : questionUpdateRequest.getUpdateAnswers()) {
                AnswerEntity answer = answerRepository.findById(updateAnswer.getId()).orElseThrow(()
                        -> new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorMessage.NOT_FOUND_ANSWER));
                if (updateAnswer.getContent() != null)
                    answer.setContent(updateAnswer.getContent());
                if (updateAnswer.getIsCorrect() != null)
                    answer.setCorrect(updateAnswer.getIsCorrect());
                list.addIfNotExisted(answer, new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorMessage.DUPLICATE_ANSWER_ID));
            }
        }

        answerRepository.saveAll(list);

        QuestionEntity updatedQuestion = questionRepository.save(question);

        return Question.fromEntity(updatedQuestion);
    }

    @Override
    @Transactional
    public void deleteQuestion(UUID questionId) {
        QuestionEntity question = questionRepository.findById(questionId).orElseThrow(()
                -> new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorMessage.NOT_FOUND_QUESTION));
        List<AnswerEntity> answers = question.getAnswers();
        answers.forEach(answerEntity -> answerEntity.setDeleted(true));
        question.setDeleted(true);

        questionRepository.save(question);

    }

}
