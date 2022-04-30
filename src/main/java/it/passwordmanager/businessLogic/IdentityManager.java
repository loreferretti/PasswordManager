package it.passwordmanager.businessLogic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;

public class IdentityManager {
    public boolean authenticate(String password) {
        boolean valid = false;
        try(FileReader reader = new FileReader("src/main/resources/.passwordManager.properties")) {
            Properties prop = new Properties();
            prop.load(reader);
            byte[] masterPassword = Base64.getDecoder().decode(prop.getProperty("masterPassword"));
            byte[] salt = Base64.getDecoder().decode(prop.getProperty("salt"));
            valid = checkLogin(password, masterPassword, salt);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return valid;
    }

    private boolean checkLogin(String password, byte[] masterPassword, byte[] salt) {
        EncryptionService encryptionService = new EncryptionService();
        byte[] passwordEncrypted = encryptionService.getEncryptedPassword(password, salt);
        return Arrays.equals(masterPassword, passwordEncrypted);
    }

}
