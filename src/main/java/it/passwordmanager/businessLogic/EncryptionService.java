package it.passwordmanager.businessLogic;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class EncryptionService {
    public byte[] getEncryptedPassword(String password, byte[] salt) {
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

    public byte[] generateSalt() {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[8];
            random.nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] padding(String key) {
        int charsToAdd = 16 - (key.length() % 16 );
        for(char c = 'a'; c < 'a'+ charsToAdd; c++) {
            key += c;
        }
        return key.getBytes();
    }


    public String encrypt(String keyVal, String field) {
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

    public String decrypt(String keyVal, String field) {
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

    /*public void DbEncryption(int cipherMode, String keyVal, File inputFile, File outputFile) {

        try {
            Key key = new SecretKeySpec(padding(keyVal), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(cipherMode, key);


            byte[] inputBytes;
            try (FileInputStream inputStream = new FileInputStream(inputFile)) {
                inputBytes = new byte[(int) inputFile.length()];
                inputStream.read(inputBytes);
            }

            byte[] outputBytes = cipher.doFinal(inputBytes);
            try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                outputStream.write(outputBytes);
            }

        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }

    }*/

}