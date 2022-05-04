package it.passwordmanager.businessLogic;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionFactoryTest {

    @Rule
    TemporaryFolder folder = new TemporaryFolder();

    @Test
    void getConnection() throws IOException, SQLException {
        folder.create();
        File db = folder.newFile("db-login-test.db");
        String path = "jdbc:sqlite:db-login-test.db";
        Connection connection = ConnectionFactory.getConnection(path);
        assertNotNull(connection);
    }
}