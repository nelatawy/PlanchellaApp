package com.planchella.utils;

import java.security.SecureRandom;

public class IdGenerator {
    private static final SecureRandom random = new SecureRandom();

    public static Long generateId() {
        // Generate a positive long ID (excluding 0)
        return Math.abs(random.nextLong()) % (Integer.MAX_VALUE - 1) + 1;
    }
}
