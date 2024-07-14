package com.study.flashcardaibackend.model.question;

import com.study.flashcardaibackend.model.answer.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDetail extends Question {
    List<Answer> answers;

    public QuestionDetail(Question question, List<Answer> answers) {
        super(question.getId(), question.getTitle(), question.isDeleted(),
                question.getQuestionType(), question.getSetId(), question.getCreatedAt(), question.getUpdatedAt());
        this.answers = answers;
    }
}
