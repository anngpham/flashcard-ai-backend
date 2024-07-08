package com.study.flashcardaibackend.security.jwt;

public class JwtConstants {

    public static final String AUTH_HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer";

    public static final long EXPIRATION_TIME = 30 * 60 * 1000; // 30 minutes
}
