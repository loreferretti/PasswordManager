package it.passwordmanager.businessLogic;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class IdentityManagerTest {

    final String URL = "passwordManagerTest.properties";

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    void authenticate() throws IOException {
        folder.create();
        File fileProp = folder.newFile(URL);
        IdentityManager im = new IdentityManager(fileProp.getPath());
        String password = "myPassword";
        im.authenticate(password);
        assertTrue(im.authenticate(password));
    }
}