package com.study.flashcardaibackend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionCreationRequest {

    @NotBlank(message = "question title not blank")
    private String title;

    @Pattern(regexp = "TEXT_FILL|MULTIPLE_CHOICE|CHECKBOXES", message = "question type must be TEXT_FILL, MULTIPLE_CHOICE, or CHECKBOXES")
    private String questionType;

    @NotNull(message = "answers not null")
    @Valid
    private List<AnswerRequest> newAnswers;


    public boolean isValidQuestion(){

        int numberOfCorrectAnswers = 0;

        for(AnswerRequest answer : newAnswers){
            if(answer.isCorrect())
                numberOfCorrectAnswers++;
        }

        // check Question is valid or not
        if (numberOfCorrectAnswers == 0){
            return false;
        } else if((questionType.equals("TEXT_FILL") || questionType.equals("MULTIPLE_CHOICE")) && numberOfCorrectAnswers == 1){
            return true;
        } else return questionType.equals("CHECKBOXES") && numberOfCorrectAnswers >= 1;
    }
}
