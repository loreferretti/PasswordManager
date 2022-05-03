package it.passwordmanager.dao;

import it.passwordmanager.businessLogic.ConnectionFactory;
import it.passwordmanager.businessLogic.EncryptionService;
import it.passwordmanager.domainModel.Login;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoginDaoTest {

    final String dbName = "db-login-test.db";
    final String dbPath = String.format("jdbc:sqlite:%s", dbName);
    String password = "MyPassword";

    @Rule
    TemporaryFolder folder= new TemporaryFolder();

    @Before
    void setupDb() throws IOException, SQLException {
        folder.create();
        File db = folder.newFile(dbName);
        String path = "jdbc:sqlite:db-login-test.db";
        Connection connection = ConnectionFactory.getConnection(dbPath);
        if(connection != null) {
            DatabaseMetaData meta = connection.getMetaData();
            String query = "CREATE TABLE \"Login\" (\n" +
                    " \"id\" INTEGER,\n" +
                    " \"website\" TEXT NOT NULL,\n" +
                    " \"username\" TEXT NOT NULL,\n" +
                    " \"password\" TEXT NOT NULL,\n" +
                    " UNIQUE(\"website\",\"username\",\"password\"),\n" +
                    " PRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                    ");";
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.execute();
        }

    }

    @Test
    void createAndReadAll() {
        LoginDao loginDao = new LoginDao(dbPath);
        List<Login> logins;
        Login login = new Login("someWebsite", "someUsername", "somePassword");
        if(loginDao.create(password, login)) {
            logins = loginDao.getAll(password);
            assertEquals(1, logins.size());
            assertEquals(logins.get(0), login);
        }


    }

    @Test
    void read() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}