package com.study.flashcardaibackend.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public final class HashingUtil {
    private static final int STRENGTH = 12;

    // Use Argon2PasswordEncoder to hash and verify passwords
    private static final BCryptPasswordEncoder bCryptPwEncoder = new BCryptPasswordEncoder(STRENGTH);

    public static String hashPassword(String rawPassword) {
        return bCryptPwEncoder.encode(rawPassword);
    }

    public static boolean verifyPassword(String inputPassword, String hashPassword) {
        return bCryptPwEncoder.matches(inputPassword, hashPassword);
    }

}