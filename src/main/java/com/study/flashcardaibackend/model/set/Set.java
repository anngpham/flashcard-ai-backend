package com.study.flashcardaibackend.model.set;

import com.study.flashcardaibackend.entity.set.SetEntity;
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
public class Set {
    private UUID id;

    private String title;

    private String description;

    private Boolean isDeleted;

    private UUID ownerId;

    private boolean isPublic;

    private Date createdAt;

    private Date updatedAt;

    public static Set fromEntity(SetEntity setEntity) {
        return new Set(
                setEntity.getId(),
                setEntity.getTitle(),
                setEntity.getDescription(),
                setEntity.isDeleted(),
                setEntity.getOwner().getId(),
                setEntity.isPublic(),
                setEntity.getCreatedAt(),
                setEntity.getUpdatedAt()
        );
    }

}
