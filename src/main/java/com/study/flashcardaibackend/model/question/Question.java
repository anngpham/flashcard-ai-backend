package com.study.flashcardaibackend.model.question;

import com.study.flashcardaibackend.entity.question.QuestionEntity;
import com.study.flashcardaibackend.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    private UUID id;

    private String title;

    private Boolean isDeleted;

    private QuestionType questionType;

    private UUID setId;

    private Date createdAt;

    private Date updatedAt;

    public static Question fromEntity(QuestionEntity questionEntity) {
        return new Question(
                questionEntity.getId(),
                questionEntity.getTitle(),
                questionEntity.isDeleted(),
                questionEntity.getQuestionType(),
                questionEntity.getSet().getId(),
                questionEntity.getCreatedAt(),
                questionEntity.getUpdatedAt()
        );
    }

}
