package it.passwordmanager.businessLogic;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class IdentityManagerTest {

    private final String URL = "passwordManagerTest.properties";

    @Rule
    private TemporaryFolder folder= new TemporaryFolder();

    @Test
    public void authenticate() throws IOException {
        folder.create();
        File fileProp = folder.newFile(URL);
        IdentityManager im = new IdentityManager(fileProp.getPath());
        String password = "myPassword";
        im.authenticate(password);
        assertTrue(im.authenticate(password));
    }
}