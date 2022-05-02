package it.passwordmanager.businessLogic;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class EncryptionService {
    public byte[] getEncryptedPassword(String password, byte[] salt) {
        try {
            String algorithm = "PBKDF2WithHmacSHA1";
            int derivedKeyLength = 160;
            int iterations = 20000;
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);
            SecretKeyFactory f =  SecretKeyFactory.getInstance(algorithm);
            return f.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
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

    public void DbEncryption(int cipherMode, String password, File inputFile, File outputFile) {

        try {
            Key key = new SecretKeySpec(password.getBytes(), "AES");
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



        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IOException |
                 BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }

    }

}
