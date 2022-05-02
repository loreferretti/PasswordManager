package it.passwordmanager.businessLogic;

import it.passwordmanager.dao.Dao;
import it.passwordmanager.domainModel.Login;

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

    public boolean storeAndEncryptPassword(String password) {
        return false;
    }

    public boolean authenticate(String password) {

        proxy = new AuthenticationProxy(password);

        return false;
    }

}
