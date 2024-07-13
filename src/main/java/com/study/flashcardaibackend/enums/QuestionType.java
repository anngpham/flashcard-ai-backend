package com.study.flashcardaibackend.enums;

public enum QuestionType {

    TEXT_FILL, MULTIPLE_CHOICE, CHECKBOXES;

    public static final String regex = "TEXT_FILL|MULTIPLE_CHOICE|CHECKBOXES";
    public static final String regexMessage = "question type must be in " + regex;
}
