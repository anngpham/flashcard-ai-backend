package com.study.flashcardaibackend.dto.question;

import com.study.flashcardaibackend.dto.answer.AnswerCreationBodyDTO;
import com.study.flashcardaibackend.dto.answer.AnswerUpdateBodyDTO;
import com.study.flashcardaibackend.enums.QuestionType;
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
public class QuestionUpdateBodyDTO {

    @NotBlank(message = "question title not blank")
    private String title;

    @Pattern(regexp = QuestionType.regex, message = QuestionType.regexMessage)
    private String questionType;

    @Nullable
    @Valid
    private List<AnswerCreationBodyDTO> newAnswers;

    @Nullable
    @Valid
    private List<AnswerUpdateBodyDTO> updateAnswers;

    @Nullable
    private List<UUID> deleteAnswers;
}
