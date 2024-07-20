package com.ssafy.account.common.domain.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {
    @Value("${hash.pepper}")
    private static String pepper;

    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword+pepper, BCrypt.gensalt());
    }

    public static boolean checkPass(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword+pepper, hashedPassword);
    }
}
