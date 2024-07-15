package com.study.flashcardaibackend.dto.question;

import com.study.flashcardaibackend.dto.answer.AnswerCreationBodyDTO;
import com.study.flashcardaibackend.enums.QuestionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionCreationBodyDTO {

    @NotBlank(message = "question title not blank")
    private String title;

    @Pattern(regexp = QuestionType.regex, message = QuestionType.regexMessage)
    private String questionType;

    @NotEmpty(message = "list answers cannot be empty")
    @Valid
    private List<AnswerCreationBodyDTO> newAnswers;

}
