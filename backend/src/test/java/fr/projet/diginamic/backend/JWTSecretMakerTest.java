package fr.projet.diginamic.backend;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;

/** Test class for JWTSecretMaker */
public class JWTSecretMakerTest {

    /** Generate secret key for JWT encoded with HmacSHA256 */
    @Test
    public void generateSecretKey() {
        SecretKey secret = Jwts.SIG.HS256.key().build();
        String encoded = DatatypeConverter.printHexBinary(secret.getEncoded());
        System.out.println("Encoded Key: " + encoded);
    }
}