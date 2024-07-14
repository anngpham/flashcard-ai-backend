package com.study.flashcardaibackend.util;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;


public final class HashingUtil {
    private static final int SALT_LENGTH = 16; // Generate a 16 byte salt
    private static final int HASH_LENGTH = 32; // Generate a 32 byte (256 bit) hash
    private static final int PARALLELISM = 1; // Use 1 threads
    private static final int MEMORY = 60000; // Use 64 MB of memory
    private static final int ITERATIONS = 10; // Run 3 iterations

    // Use Argon2PasswordEncoder to hash and verify passwords
    private static final Argon2PasswordEncoder argonPwEncoder = new Argon2PasswordEncoder(SALT_LENGTH, HASH_LENGTH, PARALLELISM, MEMORY, ITERATIONS);

    public static String hashPassword(String rawPassword) {
        return argonPwEncoder.encode(rawPassword);
    }

    public static boolean verifyPassword(String inputPassword, String hashPassword) {
        return argonPwEncoder.matches(inputPassword, hashPassword);
    }

    public static String hashPassword2(String rawPassword) {
        return rawPassword;
    }

    public static boolean verifyPassword2(String inputPassword, String hashPassword) {
        return inputPassword.equals(hashPassword);
    }
}