package it.passwordmanager.businessLogic;

import it.passwordmanager.dao.Dao;
import it.passwordmanager.domainModel.Login;

import java.io.*;
import java.util.List;

public class LoginController {

    private static LoginController controller;

    private Dao<Login> proxy;
    private String password;

    public static LoginController getInstance() {
        if (controller == null)
            controller = new LoginController();

        return controller;
    }

    private LoginController() {
        this.proxy = new AuthenticationProxy();
    }

    private LoginController(String password) {
        this.proxy = new AuthenticationProxy();
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Login> getAll() {
        return proxy.getAll(password);
    }

    public void addLogin(Login login) {
        proxy.create(password, login);
    }

    public void updateLogin(Login login) {
        proxy.update(password, login);
    }

    public void deleteLogin(Login login) {
        proxy.delete(login);
    }

    public List<Login> searchByWebsite(String searchString) {
        return proxy.read(password, searchString);
    }

}
