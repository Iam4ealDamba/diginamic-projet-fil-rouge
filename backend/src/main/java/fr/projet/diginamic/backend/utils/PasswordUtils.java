package fr.projet.diginamic.backend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtils {
    @Autowired
    static Environment env;

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(Integer.parseInt(env.getProperty("bcrypt.hash.rounds"))));
    }

    public static boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
