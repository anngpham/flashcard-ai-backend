package com.study.flashcardaibackend.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class AnswerUpdateRequest {

    @NotNull(message = "id not null")
    private UUID id;


    @Nullable
    private String content;

    @Nullable
    private Boolean isCorrect;

    public boolean isCorrect(){
        return Boolean.TRUE.equals(isCorrect);
    }
}
