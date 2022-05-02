package it.passwordmanager.businessLogic;

import it.passwordmanager.dao.Dao;
import it.passwordmanager.domainModel.Login;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LoginController {

    private Dao<Login> proxy;

    public LoginController() {}

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

    public void onStart(String masterPassword) {
        try {
            File db = new File("db-login-encrypted.db");
            File db_temp = File.createTempFile("db-login",".db");
            db_temp.deleteOnExit();

            EncryptionService es = new EncryptionService();
            es.DbEncryption(2, es.padding(masterPassword).toString() , db, db_temp);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean storeAndEncryptPassword(String password) {
        return false;
    }

    public boolean authenticate(String password) {

        proxy = new AuthenticationProxy(password);

        return false;
    }

}
