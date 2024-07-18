package fr.projet.diginamic.backend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCrypt;

/** Password utils for users */
public class PasswordUtils {
    @Autowired
    static Environment env;

    /**
     * Hash a password
     * 
     * @param password - the password to hash
     */
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(Integer.parseInt(env.getProperty("bcrypt.hash.rounds"))));
    }

    /**
     * Check if a password matches a hash
     * 
     * @param password - the password to check
     * @param hash     - the hash to check against
     */
    public static boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
