package it.passwordmanager.businessLogic;

import it.passwordmanager.dao.Dao;
import it.passwordmanager.domainModel.Login;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LoginController {

    private Dao<Login> proxy;
    private String password;

    public LoginController() {}

    public LoginController(String password) {
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Login> getAll() {
        return proxy.getAll();
    }

    public boolean addLogin(Login login) {
        return proxy.create(login);
    }

    public boolean updateLogin(Login login) {
        return proxy.update(login);
    }

    public boolean deleteLogin(Login login) {
        return proxy.delete(login);
    }

    public List<Login> searchByWebsite(String website) {
        return proxy.read(website);
    }

    public boolean encrypt(int cipherMode) {
        return false;
    }

    public void onStart() {
        try {
            File db = new File("db-login-encrypted.db");
            File db_temp = File.createTempFile("db-login",".db");
            db_temp.deleteOnExit();

            EncryptionService es = new EncryptionService();
            es.DbEncryption(2, es.padding(password).toString(), db, db_temp);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onExit() {
        File db = new File("db-login-encrypted.db");
        File db_temp = new File("db-login.db");

        EncryptionService es = new EncryptionService();
        es.DbEncryption(1, es.padding(password).toString(), db_temp, db);
    }

    public boolean storeAndEncryptPassword(String password) {
        return false;
    }

    public boolean authenticate() {

        proxy = new AuthenticationProxy();

        return false;
    }

}
