package it.passwordmanager.dao;

import it.passwordmanager.businessLogic.EncryptionService;
import it.passwordmanager.domainModel.Login;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.*;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginDaoTest {

    private static final String dbName = "db-login-test.db";
    private static final String dbPath = String.format("jdbc:sqlite:%s", dbName);
    private static final String password = "MyPassword";
    private static final String paddedPassword = new String(EncryptionService.padding(password));

    private static File tempFile;

    @BeforeAll
    public static void init() throws SQLException {
        tempFile = new File(dbName);
        Connection connection = DriverManager.getConnection(dbPath);
        if(connection != null) {
            String queryTable = """
                    create table if not exists"Login" (
                     "id" INTEGER,
                     "website" TEXT NOT NULL,
                     "username" TEXT NOT NULL,
                     "password" TEXT NOT NULL,
                     UNIQUE("website","username"),
                     PRIMARY KEY("id" AUTOINCREMENT)
                    );""";
            PreparedStatement pstatTable = connection.prepareStatement(queryTable);
            pstatTable.execute();

            String query = "insert into Login (website, username, password) values (?,?,?)";
            PreparedStatement pstatInsert = connection.prepareStatement(query);
            pstatInsert.setString(1, EncryptionService.encrypt(paddedPassword, "someWebsite"));
            pstatInsert.setString(2, EncryptionService.encrypt(paddedPassword, "someUsername"));
            pstatInsert.setString(3, EncryptionService.encrypt(paddedPassword, "somePassword"));
            pstatInsert.execute();
            connection.close();
        }

    }

    @Test
    public void getAll() {
        LoginDao loginDao = new LoginDao(password, dbPath);
        List<Login> logins;
        logins = loginDao.getAll();
        assertEquals(1, logins.size());
        assertEquals(logins.get(0).getWebsite(), "someWebsite");
        assertEquals(logins.get(0).getUsername(), "someUsername");
        assertEquals(logins.get(0).getPassword(), "somePassword");

    }

    @Test
    public void create() {
        LoginDao loginDao = new LoginDao(password, dbPath);
        Login login = new Login("anotherWebsite", "anotherUsername", "anotherPassword");
        List<Login> logins;
        if(loginDao.create(login)) {
            logins = loginDao.getAll();
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
    public void read() {
        LoginDao loginDao = new LoginDao(password, dbPath);
        loginDao.getAll();
        String toSearch = "some";
        List<Login> logins;
        logins = loginDao.read(toSearch);
        assertEquals(1, logins.size());
        assertEquals(logins.get(0).getWebsite(), "someBeatifulWebsite");
        assertEquals(logins.get(0).getUsername(), "someBeatifulUsername");
        assertEquals(logins.get(0).getPassword(), "someBeatifulPassword");

    }

    @Test
    public void update() {
        LoginDao loginDao = new LoginDao(password, dbPath);
        List<Login> logins = loginDao.getAll();
        logins.get(0).setWebsite("someBeatifulWebsite");
        logins.get(0).setUsername("someBeatifulUsername");
        logins.get(0).setPassword("someBeatifulPassword");
        loginDao.update(logins.get(0));
        assertEquals(1, logins.size());
        assertEquals(logins.get(0).getWebsite(), "someBeatifulWebsite");
        assertEquals(logins.get(0).getUsername(), "someBeatifulUsername");
        assertEquals(logins.get(0).getPassword(), "someBeatifulPassword");


    }

    @Test
    public void delete() {
        LoginDao loginDao = new LoginDao(password, dbPath);
        List<Login> logins = loginDao.getAll();
        loginDao.delete(logins.get(1));
        logins = loginDao.getAll();
        assertEquals(1, logins.size());
        assertEquals(logins.get(0).getWebsite(), "someWebsite");
        assertEquals(logins.get(0).getUsername(), "someUsername");
        assertEquals(logins.get(0).getPassword(), "somePassword");
    }

    @AfterAll
    public static void teardown() throws SQLException {
        Connection connection = DriverManager.getConnection(dbPath);
        if(connection != null) {
            String queryDropTable = "drop table Login;";
            PreparedStatement pstat = connection.prepareStatement(queryDropTable);
            pstat.execute();
            connection.close();
        }
        tempFile.delete();
    }
}