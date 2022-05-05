package it.passwordmanager.businessLogic;

import it.passwordmanager.dao.Dao;
import it.passwordmanager.domainModel.Login;

import java.util.List;

public class LoginController {

    private static LoginController controller;

    private Dao<Login> loginDao;
    private final String propertiesPath = "src/main/resources/.passwordManager.properties";
    private final String URL = "jdbc:sqlite:db-login.db";


    public static LoginController getInstance() {
        if (controller == null)
            controller = new LoginController();

        return controller;
    }

    public void init(String password) {
        this.loginDao = new AuthenticationProxy(password, propertiesPath, URL);
    }

    public List<Login> getAll() {
        return loginDao.getAll();
    }

    public boolean addLogin(Login login) {
        return loginDao.create(login);
    }

    public boolean updateLogin(Login login) {
        return loginDao.update(login);
    }

    public boolean deleteLogin(Login login) {
        return loginDao.delete(login);
    }

    public List<Login> searchByWebsite(String searchString) {
        return loginDao.read(searchString);
    }

}
