package com.study.flashcardaibackend.service.question;

import com.study.flashcardaibackend.constant.ErrorConstants;
import com.study.flashcardaibackend.dao.QuestionRepository;
import com.study.flashcardaibackend.dto.answer.AnswerCreationBodyDTO;
import com.study.flashcardaibackend.dto.answer.AnswerUpdateBodyDTO;
import com.study.flashcardaibackend.dto.question.QuestionCreationBodyDTO;
import com.study.flashcardaibackend.dto.question.QuestionUpdateBodyDTO;
import com.study.flashcardaibackend.entity.answer.AnswerEntity;
import com.study.flashcardaibackend.entity.question.QuestionEntity;
import com.study.flashcardaibackend.entity.set.SetEntity;
import com.study.flashcardaibackend.enums.QuestionType;
import com.study.flashcardaibackend.exception.HttpRuntimeException;
import com.study.flashcardaibackend.model.answer.Answer;
import com.study.flashcardaibackend.model.common.CommonList;
import com.study.flashcardaibackend.model.common.ValidationDetail;
import com.study.flashcardaibackend.model.question.Question;
import com.study.flashcardaibackend.model.question.QuestionDetail;
import com.study.flashcardaibackend.service.answer.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerService answerService;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, AnswerService answerService) {
        this.questionRepository = questionRepository;
        this.answerService = answerService;
    }

    @Override
    public Question getQuestion(UUID questionId) {
        Optional<QuestionEntity> question = questionRepository.findById(questionId);
        return question.map(Question::fromEntity).orElse(null);
    }

    @Override
    public QuestionDetail createQuestion(QuestionCreationBodyDTO questionCreationBody, UUID setId) {
        QuestionEntity question = new QuestionEntity();
        question.setSet(SetEntity.getReferenceById(setId));
        question.setTitle(questionCreationBody.getTitle());
        question.setQuestionType(QuestionType.valueOf(questionCreationBody.getQuestionType()));


        question.setAnswers(questionCreationBody.getNewAnswers().stream().map(a -> {
            AnswerEntity answer = new AnswerEntity();
            answer.setContent(a.getContent());
            answer.setCorrect(a.getIsCorrect());
            answer.setQuestion(question);
            return answer;
        }).toList());

        ValidationDetail validationDetail = checkQuestionHasValidAnswers(question.getQuestionType(), question.getAnswers());
        if (!validationDetail.isValid()) {
            throw new HttpRuntimeException(HttpStatus.BAD_REQUEST, validationDetail.getMessage());
        }

        QuestionEntity createdQuestion = questionRepository.save(question);
        List<Answer> createdAnswers = createdQuestion.getAnswers().stream().map(Answer::fromEntity).toList();
        return new QuestionDetail(Question.fromEntity(createdQuestion), createdAnswers);

    }

    @Override
    @Transactional
    public QuestionDetail updateQuestion(QuestionUpdateBodyDTO questionUpdateBody, UUID questionId) {
        // update question
        QuestionEntity question = questionRepository.findById(questionId).get();
        if (questionUpdateBody.getTitle() != null) {
            question.setTitle(questionUpdateBody.getTitle());
        }
        if (questionUpdateBody.getQuestionType() != null) {
            question.setQuestionType(QuestionType.valueOf(questionUpdateBody.getQuestionType()));
        }
        QuestionEntity updatedQuestion = questionRepository.save(question);

        // add new answers, update answers, delete answers to List and save all
        CommonList<AnswerEntity> list = new CommonList<>();
        // add new answers to List
        if (questionUpdateBody.getNewAnswers() != null) {
            for (AnswerCreationBodyDTO newAnswer : questionUpdateBody.getNewAnswers()) {
                AnswerEntity answer = new AnswerEntity();
                answer.setContent(newAnswer.getContent());
                answer.setCorrect(newAnswer.getIsCorrect());
                answer.setQuestion(updatedQuestion);
                list.add(answer);
            }
        }
        // add update answers to List
        if (questionUpdateBody.getUpdateAnswers() != null) {
            for (AnswerUpdateBodyDTO updateAnswer : questionUpdateBody.getUpdateAnswers()) {
                AnswerEntity answer = answerService.getAnswerById(updateAnswer.getId()).orElseThrow(()
                        -> new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorConstants.ANSWER_NOT_FOUND));
                if (answer.isDeleted()) {
                    throw new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorConstants.ANSWER_IS_DELETED);
                }
                if (!answer.getQuestion().getId().equals(questionId)) {
                    throw new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorConstants.ANSWER_NOT_BELONG_TO_QUESTION);
                }
                if (updateAnswer.getContent() != null)
                    answer.setContent(updateAnswer.getContent());
                if (updateAnswer.getIsCorrect() != null)
                    answer.setCorrect(updateAnswer.getIsCorrect());
                list.addIfNotExisted(answer, new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorConstants.ANSWER_IS_DUPLICATED));
            }
        }
        // add delete answers to List
        if (questionUpdateBody.getDeleteAnswers() != null) {
            for (UUID id : questionUpdateBody.getDeleteAnswers()) {
                AnswerEntity answer = answerService.getAnswerById(id).orElseThrow(()
                        -> new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorConstants.ANSWER_NOT_FOUND));
                if (answer.isDeleted()) {
                    throw new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorConstants.ANSWER_IS_DELETED);
                }
                if (!answer.getQuestion().getId().equals(questionId)) {
                    throw new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorConstants.ANSWER_NOT_BELONG_TO_QUESTION);
                }
                answer.setDeleted(true);
                list.addIfNotExisted(answer, new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorConstants.ANSWER_IS_DUPLICATED));
            }
        }
        // save all answers of List
        answerService.saveAll(list);

        // after saving, get list answers of question again and check
        ValidationDetail validationDetail = checkQuestionHasValidAnswers(updatedQuestion.getQuestionType(), answerService.getAllAnswersByQuestionId(questionId));
        if (!validationDetail.isValid()) {
            throw new HttpRuntimeException(HttpStatus.BAD_REQUEST, validationDetail.getMessage());
        }

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

    private ValidationDetail checkQuestionHasValidAnswers(QuestionType questionType, List<AnswerEntity> answers) {
        int numberOfAnswers = (int) answers.stream().filter(a -> !a.isDeleted()).count();
        int numberOfCorrectAnswers = (int) answers.stream().filter(a -> a.isCorrect() && !a.isDeleted()).count();
        switch (questionType) {
            case TEXT_FILL: {
                if (numberOfAnswers != 1 || numberOfCorrectAnswers != 1) {
                    return new ValidationDetail(false,
                            "Question type text_fill must have exactly 1 correct answer");
                }
                break;
            }
            case MULTIPLE_CHOICE: {
                if (numberOfAnswers < 2 || numberOfAnswers > 5 || numberOfCorrectAnswers != 1) {
                    return new ValidationDetail(false,
                            "Question type multiple_choice must have 2-5 answers (1 correct answer)");
                }
                break;
            }
            case CHECKBOXES: {
                if (numberOfAnswers < 2 || numberOfAnswers > 5 || numberOfCorrectAnswers < 1) {
                    return new ValidationDetail(false,
                            "Question type checkboxes must have 2-5 answers (at least 1 correct answer)");
                }
                break;
            }
        }
        return new ValidationDetail(true, null);
    }

}
