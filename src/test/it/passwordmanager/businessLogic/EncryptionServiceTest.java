package it.passwordmanager.businessLogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionServiceTest {

    @Test
    void encryptDecrypt() {
        String keyVal = "myPassword";
        String field = "This is the field to be encrypt";
        String fieldEncrypted = EncryptionService.encrypt(keyVal, field);
        assertEquals(field, EncryptionService.decrypt(keyVal,fieldEncrypted));
    }
}