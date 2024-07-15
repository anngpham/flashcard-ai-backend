package com.study.flashcardaibackend.service.question;

import com.study.flashcardaibackend.constant.ErrorConstants;
import com.study.flashcardaibackend.dao.AnswerRepository;
import com.study.flashcardaibackend.dao.QuestionRepository;
import com.study.flashcardaibackend.dao.SetRepository;
import com.study.flashcardaibackend.dto.answer.AnswerCreationBodyDTO;
import com.study.flashcardaibackend.dto.answer.AnswerUpdateBodyDTO;
import com.study.flashcardaibackend.dto.question.QuestionCreationBodyDTO;
import com.study.flashcardaibackend.dto.question.QuestionUpdateBodyDTO;
import com.study.flashcardaibackend.entity.answer.AnswerEntity;
import com.study.flashcardaibackend.entity.question.QuestionEntity;
import com.study.flashcardaibackend.enums.QuestionType;
import com.study.flashcardaibackend.exception.HttpRuntimeException;
import com.study.flashcardaibackend.model.answer.Answer;
import com.study.flashcardaibackend.model.common.CommonList;
import com.study.flashcardaibackend.model.common.ValidationDetail;
import com.study.flashcardaibackend.model.question.Question;
import com.study.flashcardaibackend.model.question.QuestionDetail;
import com.study.flashcardaibackend.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
    public Question getQuestion(UUID questionId) {
        Optional<QuestionEntity> question = questionRepository.findById(questionId);
        return question.map(Question::fromEntity).orElse(null);
    }

    @Override
    public QuestionDetail createQuestion(QuestionCreationBodyDTO questionCreationBody, UUID setId) {
        QuestionEntity question = new QuestionEntity();
        question.setSet(setRepository.getReferenceById(setId));
        question.setTitle(questionCreationBody.getTitle());
        question.setQuestionType(QuestionType.valueOf(questionCreationBody.getQuestionType()));


        question.setAnswers(questionCreationBody.getNewAnswers().stream().map(a -> {
            AnswerEntity answer = new AnswerEntity();
            answer.setContent(a.getContent());
            answer.setCorrect(a.getIsCorrect());
            answer.setQuestion(question);
            return answer;
        }).toList());

        checkQuestionHasValidAnswers(question);

        QuestionEntity createdQuestion = questionRepository.save(question);
        List<Answer> createdAnswers = createdQuestion.getAnswers().stream().map(Answer::fromEntity).toList();
        return new QuestionDetail(Question.fromEntity(createdQuestion), createdAnswers);

    }

    @Override
    @Transactional
    public QuestionDetail updateQuestion(QuestionUpdateBodyDTO questionUpdateBody, UUID questionId) {
        QuestionEntity question = questionRepository.findById(questionId).get();
        if (questionUpdateBody.getTitle() != null) {
            question.setTitle(questionUpdateBody.getTitle());
        }
        if (questionUpdateBody.getQuestionType() != null) {
            question.setQuestionType(QuestionType.valueOf(questionUpdateBody.getQuestionType()));
        }

        CommonList<AnswerEntity> list = new CommonList<>();
        if (questionUpdateBody.getNewAnswers() != null) {
            addNewAnswersToList(questionUpdateBody.getNewAnswers(), question, list);
        }
        if (questionUpdateBody.getUpdateAnswers() != null) {
            addUpdateAnswersToList(questionUpdateBody.getUpdateAnswers(), list);
        }
        if (questionUpdateBody.getDeleteAnswers() != null) {
            addDeleteAnswersToList(questionUpdateBody.getDeleteAnswers(), list);
        }

        answerRepository.saveAll(list);
        QuestionEntity updatedQuestion = questionRepository.save(question);

        // after saving, get list answers of question again and check
        checkQuestionHasValidAnswers(updatedQuestion);

        Question questionModel = Question.fromEntity(updatedQuestion);
        List<Answer> answers = updatedQuestion.getAnswers().stream().map(Answer::fromEntity).toList();
        return new QuestionDetail(questionModel, answers);
    }

    @Override
    public void deleteQuestion(UUID questionId) {
        QuestionEntity question = questionRepository.findById(questionId).get();
        question.setDeleted(true);
        questionRepository.save(question);
    }

    private void checkQuestionHasValidAnswers(QuestionEntity question) {
        ValidationDetail validationDetail = ValidationUtil.checkQuestionHasValidAnswers(question.getQuestionType(), question.getAnswers());
        if (!validationDetail.isValid()) {
            throw new HttpRuntimeException(HttpStatus.BAD_REQUEST, validationDetail.getMessage());
        }
    }

    private void addNewAnswersToList(List<AnswerCreationBodyDTO> newAnswers, QuestionEntity question, CommonList<AnswerEntity> list) {
        for (AnswerCreationBodyDTO newAnswer : newAnswers) {
            AnswerEntity answer = new AnswerEntity();
            answer.setContent(newAnswer.getContent());
            answer.setCorrect(newAnswer.getIsCorrect());
            answer.setQuestion(question);
            list.add(answer);
        }
    }

    private void addUpdateAnswersToList(List<AnswerUpdateBodyDTO> updateAnswers, CommonList<AnswerEntity> list) {
        for (AnswerUpdateBodyDTO updateAnswer : updateAnswers) {
            AnswerEntity answer = answerRepository.findById(updateAnswer.getId()).orElseThrow(()
                    -> new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorConstants.ANSWER_NOT_FOUND));
            if (updateAnswer.getContent() != null)
                answer.setContent(updateAnswer.getContent());
            if (updateAnswer.getIsCorrect() != null)
                answer.setCorrect(updateAnswer.getIsCorrect());
            list.addIfNotExisted(answer, new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorConstants.ANSWER_IS_DUPLICATED));
        }
    }

    private void addDeleteAnswersToList(List<UUID> deleteAnswers, CommonList<AnswerEntity> list) {
        for (UUID id : deleteAnswers) {
            AnswerEntity answer = answerRepository.findById(id).orElseThrow(()
                    -> new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorConstants.ANSWER_NOT_FOUND));
            answer.setDeleted(true);
            list.addIfNotExisted(answer, new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorConstants.ANSWER_IS_DUPLICATED));
        }
    }


}
