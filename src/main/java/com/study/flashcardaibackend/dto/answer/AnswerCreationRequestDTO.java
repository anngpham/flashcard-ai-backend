package com.study.flashcardaibackend.dto.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AnswerCreationRequestDTO {

    @NotBlank(message = "answer content not blank")
    private String content;

    @NotNull(message = "isCorrect not null")
    private Boolean isCorrect;

}
