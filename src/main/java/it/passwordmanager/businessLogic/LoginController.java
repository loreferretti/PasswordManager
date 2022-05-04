package it.passwordmanager.businessLogic;

import it.passwordmanager.dao.Dao;
import it.passwordmanager.domainModel.Login;

import java.util.List;

public class LoginController {

    private static LoginController controller;

    private Dao<Login> loginDao;
    private String password;

    public static LoginController getInstance() {
        if (controller == null)
            controller = new LoginController();

        return controller;
    }

    private LoginController() {
        this.loginDao = new AuthenticationProxy();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Login> getAll() {
        return loginDao.getAll(password);
    }

    public boolean addLogin(Login login) {
        return loginDao.create(password, login);
    }

    public boolean updateLogin(Login login) {
        return loginDao.update(password, login);
    }

    public void deleteLogin(Login login) {
        loginDao.delete(login);
    }

    public List<Login> searchByWebsite(String searchString) {
        return loginDao.read(searchString);
    }

}
