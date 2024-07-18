package fr.projet.diginamic.backend.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

/** Password utils for users */
public class PasswordUtils {

    /**
     * Hash a password
     * 
     * @param password - the password to hash
     */
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
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
