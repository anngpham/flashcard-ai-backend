package com.study.flashcardaibackend.constant;

public class PathConstants {
    // Spring path mapping doesn't validate UUID by default
    // We need to use a regex to validate UUID
    public static final String UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    public static final String API_PREFIX = "/api";

    public static final String AUTH = "/auth";
    public static final String AUTH_REGISTER = "/register";
    public static final String AUTH_LOGIN = "/login";

    public static final String USER = "/users";
    public static final String USER_ID = "/{userId:" + UUID_REGEX + "}";

    public static final String SET = "/sets";
    public static final String SET_ID = "/{setId:" + UUID_REGEX + "}";

    public static final String QUESTION = "/questions";
    public static final String QUESTION_ID = "/{questionId:" + UUID_REGEX + "}";

    public static final String ANSWER = "/answers";
    public static final String ANSWER_ID = "/{answerId:" + UUID_REGEX + "}";
}
