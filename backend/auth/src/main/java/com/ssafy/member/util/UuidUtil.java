package com.ssafy.member.util;

import java.util.UUID;

public class UuidUtil {
    public static String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }
}
