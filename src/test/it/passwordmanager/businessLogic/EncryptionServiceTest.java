package it.passwordmanager.businessLogic;

import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

public class EncryptionServiceTest {

    @Test
    public void encryptDecrypt() {
        String keyVal = "myPassword";
        String field = "This is the field to be encrypt";
        String fieldEncrypted = EncryptionService.encrypt(keyVal, field);
        assertEquals(field, EncryptionService.decrypt(keyVal,fieldEncrypted));
    }

    @Test
    public void getEncryptedPassword() {
        String password = "test";
        String passwordEncrypted = "u2fIA1kfob3H7igSdNiB+e+R6sk=";
        byte[] salt = Base64.getDecoder().decode("eOOJqULjj6U=");
        assertEquals(passwordEncrypted, Base64.getEncoder().encodeToString(EncryptionService.getEncryptedPassword(password,salt)));
    }

    @Test
    public void padding() {
        String key = "test";
        String paddingKey = "testabcdefghijkl";
        assertEquals(paddingKey, new String(EncryptionService.padding(key)));
    }
}