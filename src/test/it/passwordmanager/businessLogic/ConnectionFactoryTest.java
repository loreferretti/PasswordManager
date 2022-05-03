package it.passwordmanager.businessLogic;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionFactoryTest {
    @BeforeAll
    void setup() {
        File db = new File("src/test/db-login-test.db");

    }

    @Test
    void getConnection() {
    }
}