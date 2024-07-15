package com.study.flashcardaibackend.dto.answer;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class AnswerUpdateBodyDTO {

    @NotNull(message = "id not null")
    private UUID id;

    @Nullable
    private String content;

    @Nullable
    private Boolean isCorrect;

}
