package it.passwordmanager.businessLogic;

import it.passwordmanager.domainModel.Login;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthenticationProxyTest {

    private static final String dbName = "db-login-test.db";
    private static final String dbPath = String.format("jdbc:sqlite:%s", dbName);
    private static final String rightPassword = "MyRightPassword";
    private static final String wrongPassword = "MyWrongPassword";
    private static final String paddedPassword = new String(EncryptionService.padding(rightPassword));
    private static File dbFile;
    private static IdentityManager im;
    @Rule
    private static TemporaryFolder folder = new TemporaryFolder();
    private static File fileProperties;

    @BeforeAll
    public static void init() throws SQLException, IOException {
        dbFile = new File(dbName);
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
        folder.create();
        fileProperties = folder.newFile("passwordManagerTest.properties");
        im = new IdentityManager(fileProperties.getPath());
        im.authenticate(rightPassword);
    }

    @Test
    public void getAll() {
        AuthenticationProxy proxy = new AuthenticationProxy(rightPassword, fileProperties.getPath(), dbPath);
        assertNotNull(proxy.getAll());
        proxy = new AuthenticationProxy(wrongPassword, fileProperties.getPath(), dbPath);
        assertNull(proxy.getAll());
    }

    @Test
    public void create() {
        AuthenticationProxy proxy = new AuthenticationProxy(wrongPassword, fileProperties.getPath(), dbPath);
        Login login =  new Login("anotherWebsite", "anotherUsername", "anotherPassword");
        assertFalse(proxy.create(login));
        proxy = new AuthenticationProxy(rightPassword, fileProperties.getPath(), dbPath);
        assertTrue(proxy.create(login));
    }

    @Test
    public void read() {
        AuthenticationProxy proxy = new AuthenticationProxy(rightPassword, fileProperties.getPath(), dbPath);
        proxy.getAll();
        assertNotNull(proxy.read("some"));
        proxy = new AuthenticationProxy(wrongPassword, fileProperties.getPath(), dbPath);
        proxy.getAll();
        assertNull(proxy.read("some"));
    }

    @Test
    public void update() {
        AuthenticationProxy proxy = new AuthenticationProxy(rightPassword, fileProperties.getPath(), dbPath);
        List<Login> logins = proxy.getAll();
        logins.get(0).setWebsite("someBeatifulWebsite");
        logins.get(0).setUsername("someBeatifulUsername");
        logins.get(0).setPassword("someBeatifulPassword");
        assertTrue(proxy.update(logins.get(0)));
        proxy = new AuthenticationProxy(wrongPassword, fileProperties.getPath(), dbPath);
        assertFalse(proxy.update(logins.get(0)));
    }

    @Test
    public void delete() {
        AuthenticationProxy proxy = new AuthenticationProxy(rightPassword, fileProperties.getPath(), dbPath);
        List<Login> logins = proxy.getAll();
        assertTrue(proxy.delete(logins.get(1)));
        proxy = new AuthenticationProxy(wrongPassword, fileProperties.getPath(), dbPath);
        assertFalse(proxy.delete(logins.get(1)));
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
        dbFile.delete();
        folder.delete();
    }
}