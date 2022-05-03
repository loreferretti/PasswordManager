package it.passwordmanager.businessLogic;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.KeySpec;
import java.util.Base64;

public class EncryptionService {
    public static byte[] getEncryptedPassword(String password, byte[] salt) {
        try {
            String algorithm = "PBKDF2WithHmacSHA1";
            int derivedKeyLength = 160;
            int iterations = 20000;
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);
            SecretKeyFactory f =  SecretKeyFactory.getInstance(algorithm);
            return f.generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] generateSalt() {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[8];
            random.nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] padding(String key) {
        int charsToAdd = 16 - (key.length() % 16 );
        for(char c = 'a'; c < 'a'+ charsToAdd; c++) {
            key += c;
        }
        return key.getBytes();
    }


    public static String encrypt(String keyVal, String field) {
        try {
            Key key = new SecretKeySpec(padding(keyVal), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encField = cipher.doFinal(field.getBytes());
            return Base64.getEncoder().encodeToString(encField);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String keyVal, String field) {
        try {
            Key key = new SecretKeySpec(padding(keyVal), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decField = Base64.getDecoder().decode(field);
            return new String(cipher.doFinal(decField));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

}