package it.passwordmanager.businessLogic;

import it.passwordmanager.dao.Dao;
import it.passwordmanager.domainModel.Login;

import java.util.List;

public class LoginController {

    private Dao<Login> proxy;

    public boolean addLogin(Login login) {
        return false;
    }

    public boolean updateLogin(Login login) {
        return false;
    }

    public boolean deleteLogin(Login login) {
        return false;
    }

    public List<Login> searchByWebsite(String website) {
        return null;
    }

    public boolean encrypt(int cipherMode) {
        return false;
    }
}
