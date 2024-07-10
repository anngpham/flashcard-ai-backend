package com.study.flashcardaibackend.dto;

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

    @NotBlank(message = "answer content not blank")
    private String content;

    @NotNull(message = "isCorrect not null")
    private Boolean isCorrect;

    public boolean isCorrect(){
        return isCorrect;
    }
}
