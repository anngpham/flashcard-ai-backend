package com.study.flashcardaibackend.dto;

import com.study.flashcardaibackend.entity.QuestionType;
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

    @Pattern(regexp = QuestionType.regex, message = QuestionType.regexMessage)
    private String questionType;

    @NotNull(message = "answers not null")
    @Valid
    private List<AnswerCreationRequest> newAnswers;


    public boolean isValid(){

        int numberOfCorrectAnswers = numberOfCorrectAnswers(newAnswers);

        // check Question is valid or not
        if (numberOfCorrectAnswers == 0){
            return false;
        } else if((questionType.equals("TEXT_FILL") || questionType.equals("MULTIPLE_CHOICE")) && numberOfCorrectAnswers == 1){
            return true;
        } else return questionType.equals("CHECKBOXES") && numberOfCorrectAnswers >= 1;
    }

    private int numberOfCorrectAnswers(List<AnswerCreationRequest> answers) {

        int count = 0;
        for(AnswerCreationRequest answer : answers){
            if(answer.isCorrect())
                count++;
        }
        return count;
    }
}
