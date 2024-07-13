package com.study.flashcardaibackend.constant;

public class PathConstants {
    public static final String UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    public static final String USER = "/users";
    public static final String USER_ID = "/{userId:" + UUID_REGEX + "}";

    public static final String SET = "/sets";
    public static final String SET_ID = "/{setId:" + UUID_REGEX + "}";

    public static final String QUESTION = "/questions";
    public static final String QUESTION_ID = "/{questionId:" + UUID_REGEX + "}";

    public static final String ANSWER = "/answers";
    public static final String ANSWER_ID = "/{answerId:" + UUID_REGEX + "}";
}
