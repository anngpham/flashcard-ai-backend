package com.study.flashcardaibackend.entity.answer;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.study.flashcardaibackend.entity.question.QuestionEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "answers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@ToString
public class AnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "uuid DEFAULT gen_random_uuid()")
    private UUID id;

    @Column(name = "content")
    private String content;

    @Column(name = "is_correct", columnDefinition = "boolean default false")
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonBackReference
    private QuestionEntity question;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date updatedAt;

    public boolean equals(AnswerEntity ans) {
        return this.getId().equals(ans.getId());
    }
}
