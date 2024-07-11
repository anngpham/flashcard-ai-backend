package com.study.flashcardaibackend.dto;

import com.study.flashcardaibackend.entity.QuestionType;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@ToString
public class QuestionUpdateRequest {

    @NotBlank(message = "question title not blank")
    private String title;

    @Pattern(regexp = QuestionType.regex, message = QuestionType.regexMessage)
    private String questionType;


    @Nullable
    @Valid
    private List<AnswerCreationRequest> newAnswers;

    @Nullable
    @Valid
    private List<AnswerUpdateRequest> updateAnswers;

    @Nullable
    private List<UUID> deleteAnswers;

}
