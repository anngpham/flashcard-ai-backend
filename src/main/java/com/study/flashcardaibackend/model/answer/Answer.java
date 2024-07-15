package com.study.flashcardaibackend.model.answer;

import com.study.flashcardaibackend.entity.answer.AnswerEntity;
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
public class Answer {
    private UUID id;

    private String content;

    private boolean isCorrect;

    private Boolean isDeleted;

    private Date createdAt;

    private Date updatedAt;

    public static Answer fromEntity(AnswerEntity answerEntity) {
        return new Answer(
                answerEntity.getId(),
                answerEntity.getContent(),
                answerEntity.getIsCorrect(),
                answerEntity.getIsDeleted(),
                answerEntity.getCreatedAt(),
                answerEntity.getUpdatedAt()
        );
    }
}
