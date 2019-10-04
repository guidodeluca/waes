package com.example.waes.test.utils;

import java.util.UUID;

public class KeyUtils {
    public static String createKey() {
        return UUID.randomUUID().toString();
    }
}
