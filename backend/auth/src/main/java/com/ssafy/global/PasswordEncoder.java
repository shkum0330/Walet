package com.ssafy.global;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.Transient;

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

