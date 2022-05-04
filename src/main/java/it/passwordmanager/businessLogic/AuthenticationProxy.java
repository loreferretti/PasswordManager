package it.passwordmanager.businessLogic;

import it.passwordmanager.dao.Dao;
import it.passwordmanager.dao.LoginDao;
import it.passwordmanager.domainModel.Login;

import java.util.List;

public class AuthenticationProxy implements Dao<Login> {
    private LoginDao loginDao;
    private boolean authenticated = false;
    private final String propertiesPath = "src/main/resources/.passwordManager.properties";
    private final String URL = "jdbc:sqlite:db-login.db";

    @Override
    public List<Login> getAll(String password) {
        if(!authenticated) {
            if(!authenticate(password))
                return null;
        }
        return loginDao.getAll(password);
    }

    @Override
    public boolean create(String password, Login login) {
            return authenticated && loginDao.create(password, login);
    }

    @Override
    public List<Login> read(Object obj) {
            return authenticated ? loginDao.read(obj) : null;
    }

    @Override
    public boolean update(String password, Login login) {
            return authenticated && loginDao.update(password, login);
    }

    @Override
    public void delete(Login login) {
        if(authenticated)
            loginDao.delete(login);
    }

    private boolean authenticate(String password) {

        IdentityManager im = new IdentityManager(propertiesPath);
        boolean authentication = false;

        if(im.authenticate(password)) {
            loginDao = new LoginDao(URL);
            authenticated = true;
            authentication = true;
        }

        return authentication;
    }
}
