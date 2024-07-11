package com.study.flashcardaibackend.entity;

public enum QuestionType {

    TEXT_FILL, MULTIPLE_CHOICE, CHECKBOXES;

    public static final String regex = "TEXT_FILL|MULTIPLE_CHOICE|CHECKBOXES";
    public static final String regexMessage = "question type must be " + regex;
}
