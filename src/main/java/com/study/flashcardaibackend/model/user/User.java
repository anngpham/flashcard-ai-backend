package com.study.flashcardaibackend.model.user;

import com.study.flashcardaibackend.entity.user.UserEntity;
import com.study.flashcardaibackend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private UUID id;

    private String email;

    private Role role;

    private Date createdAt;

    private Date updatedAt;

    public static User fromEntity(UserEntity userEntity) {
        return new User(
            userEntity.getId(),
            userEntity.getEmail(),
            userEntity.getRole(),
            userEntity.getCreatedAt(),
            userEntity.getUpdatedAt()
        );
    }

}
