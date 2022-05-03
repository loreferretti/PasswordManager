package it.passwordmanager.businessLogic;

import java.io.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;

public class IdentityManager {
    public static boolean authenticate(String password) {
        boolean valid = false;
        File fileProperties = new File("src/main/resources/.passwordManager.properties");
        try(FileReader reader = new FileReader(fileProperties)) {
            Properties prop = new Properties();
            prop.load(reader);
            if(prop.getProperty("masterPassword") != null && prop.getProperty("salt") != null) {
                byte[] masterPassword = Base64.getDecoder().decode(prop.getProperty("masterPassword"));
                byte[] salt = Base64.getDecoder().decode(prop.getProperty("salt"));
                valid = checkLogin(password, masterPassword, salt);
            } else {
                storePassword(prop, fileProperties, password);
                valid = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return valid;
    }

    private static void storePassword(Properties prop, File fileProperties, String password) {
        try(FileOutputStream fos = new FileOutputStream(fileProperties)) {
            byte[] salt = EncryptionService.generateSalt();
            prop.setProperty("masterPassword", Base64.getEncoder().encodeToString(EncryptionService.getEncryptedPassword(password, salt)));
            prop.setProperty("salt", Base64.getEncoder().encodeToString(salt));
            prop.store(fos, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static boolean checkLogin(String password, byte[] masterPassword, byte[] salt) {
        byte[] masterPasswordEncrypted = EncryptionService.getEncryptedPassword(password, salt);
        return Arrays.equals(masterPassword, masterPasswordEncrypted);
    }

}
