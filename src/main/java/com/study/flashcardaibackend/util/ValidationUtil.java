package com.study.flashcardaibackend.util;

import com.study.flashcardaibackend.enums.QuestionType;
import com.study.flashcardaibackend.model.common.ValidationDetail;

import java.util.List;


public final class ValidationUtil {
    private abstract class AbstractValidatableAnswer {
        abstract boolean getIsCorrect();

        abstract boolean getIsDeleted();
    }
    
    public static ValidationDetail checkQuestionHasValidAnswers(QuestionType questionType, List<AbstractValidatableAnswer> answers) {
        int numberOfAnswers = answers.size();
        int numberOfCorrectAnswers = (int) answers.stream().filter(a -> a.getIsCorrect() && !a.getIsDeleted()).count();
        switch (questionType) {
            case TEXT_FILL: {
                if (numberOfAnswers != 1 || numberOfCorrectAnswers != 1) {
                    return new ValidationDetail(false,
                            "Question type text_fill must have exactly 1 correct answer");
                }
                break;
            }
            case MULTIPLE_CHOICE: {
                if (numberOfAnswers < 2 || numberOfAnswers > 5 || numberOfCorrectAnswers != 1) {
                    return new ValidationDetail(false,
                            "Question type multiple_choice must have 2-5 answers (1 correct answer)");
                }
                break;
            }
            case CHECKBOXES: {
                if (numberOfAnswers < 2 || numberOfAnswers > 5 || numberOfCorrectAnswers < 1) {
                    return new ValidationDetail(false,
                            "Question type checkboxes must have 2-5 answers (at least 1 correct answer)");
                }
                break;
            }
        }
        return new ValidationDetail(true, null);
    }


}
