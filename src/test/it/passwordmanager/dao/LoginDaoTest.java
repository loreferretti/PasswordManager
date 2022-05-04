package it.passwordmanager.dao;

import it.passwordmanager.businessLogic.ConnectionFactory;
import it.passwordmanager.businessLogic.EncryptionService;
import it.passwordmanager.domainModel.Login;
import org.junit.jupiter.api.*;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoginDaoTest {

    final String dbName = "db-login-test.db";
    final String dbPath = String.format("jdbc:sqlite:%s", dbName);
    final String password = "MyPassword";
    final String paddedPassword = new String(EncryptionService.padding(password));

    @Rule
    static TemporaryFolder folder= new TemporaryFolder();

    @BeforeEach
    void init() throws IOException, SQLException {
        folder.create();
        folder.newFile(dbName);
        Connection connection = ConnectionFactory.getConnection(dbPath);
        if(connection != null) {
            String queryTable = "create table if not exists\"Login\" (\n" +
                    " \"id\" INTEGER,\n" +
                    " \"website\" TEXT NOT NULL,\n" +
                    " \"username\" TEXT NOT NULL,\n" +
                    " \"password\" TEXT NOT NULL,\n" +
                    " UNIQUE(\"website\",\"username\",\"password\"),\n" +
                    " PRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                    ");";
            PreparedStatement pstatTable = connection.prepareStatement(queryTable);
            pstatTable.execute();

            String query = "insert into Login (website, username, password) values (?,?,?)";
            PreparedStatement pstatInsert = connection.prepareStatement(query);
            pstatInsert.setString(1, EncryptionService.encrypt(paddedPassword, "someWebsite"));
            pstatInsert.setString(2, EncryptionService.encrypt(paddedPassword, "someUsername"));
            pstatInsert.setString(3, EncryptionService.encrypt(paddedPassword, "somePassword"));
            pstatInsert.execute();
        }

    }

    @Test
    void getAll() {
        LoginDao loginDao = new LoginDao(dbPath);
        List<Login> logins;
        logins = loginDao.getAll(password);
        assertEquals(1, logins.size());
        assertEquals(logins.get(0).getWebsite(), "someWebsite");
        assertEquals(logins.get(0).getUsername(), "someUsername");
        assertEquals(logins.get(0).getPassword(), "somePassword");

    }

    @Test
    void create() {
        LoginDao loginDao = new LoginDao(dbPath);
        Login login = new Login("anotherWebsite", "anotherUsername", "anotherPassword");
        List<Login> logins;
        if(loginDao.create(password, login)) {
            logins = loginDao.getAll(password);
            assertEquals(2, logins.size());
            assertEquals(logins.get(0).getWebsite(), "someWebsite");
            assertEquals(logins.get(0).getUsername(), "someUsername");
            assertEquals(logins.get(0).getPassword(), "somePassword");
            assertEquals(logins.get(1).getWebsite(), "anotherWebsite");
            assertEquals(logins.get(1).getUsername(), "anotherUsername");
            assertEquals(logins.get(1).getPassword(), "anotherPassword");
        }

    }

    @Test
    void read() {
        LoginDao loginDao = new LoginDao(dbPath);
        loginDao.getAll(password);
        String toSearch = "some";
        List<Login> logins;
        logins = loginDao.read(toSearch);
        assertEquals(1, logins.size());
        assertEquals(logins.get(0).getWebsite(), "someWebsite");
        assertEquals(logins.get(0).getUsername(), "someUsername");
        assertEquals(logins.get(0).getPassword(), "somePassword");

    }

    @Test
    void update() {

    }

    @Test
    void delete() {
    }

    @AfterEach
    void teardown() throws SQLException {
        Connection connection = ConnectionFactory.getConnection(dbPath);
        if(connection != null) {
            String queryDropTable = "drop table Login;";
            PreparedStatement pstat = connection.prepareStatement(queryDropTable);
            pstat.execute();
        }
        //FIXME doesnt't delete file
        folder.delete();
    }
}