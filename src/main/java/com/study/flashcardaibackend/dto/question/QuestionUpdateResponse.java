package com.study.flashcardaibackend.dto.question;

import com.study.flashcardaibackend.dto.common.SuccessResponseDTO;
import com.study.flashcardaibackend.model.question.QuestionDetail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionUpdateResponse extends SuccessResponseDTO<QuestionDetail> {

    public QuestionUpdateResponse(QuestionDetail questionDetail) {
        super(questionDetail);
    }

    @Override
    public QuestionDetail getData() {
        QuestionDetail questionDetail = super.getData();
        questionDetail.setIsDeleted(null); // remove deleted field from response for security reasons
        questionDetail.getAnswers().forEach(answer -> answer.setIsDeleted(null)); // remove deleted field from response for security reasons
        return questionDetail;
    }
}