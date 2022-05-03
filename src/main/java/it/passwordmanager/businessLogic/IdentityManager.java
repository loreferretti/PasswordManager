package it.passwordmanager.businessLogic;

import java.io.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;

public class IdentityManager {
    public boolean authenticate(String password) {
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

    private void storePassword(Properties prop, File fileProperties, String password) {
        try(FileOutputStream fos = new FileOutputStream(fileProperties)) {
            EncryptionService es = new EncryptionService();
            byte[] salt = es.generateSalt();
            prop.setProperty("masterPassword", Base64.getEncoder().encodeToString(es.getEncryptedPassword(password, salt)));
            prop.setProperty("salt", Base64.getEncoder().encodeToString(salt));
            prop.store(fos, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean checkLogin(String password, byte[] masterPassword, byte[] salt) {
        EncryptionService encryptionService = new EncryptionService();
        byte[] masterPasswordEncrypted = encryptionService.getEncryptedPassword(password, salt);
        return Arrays.equals(masterPassword, masterPasswordEncrypted);
    }

}
