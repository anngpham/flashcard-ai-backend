package com.study.flashcardaibackend.constant;

public class JwtConstants {

    public static final String AUTH_HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer";

    public static final long EXPIRATION_TIME = 60 * 60 * 1000 * 24 * 7; // 7 days
}
