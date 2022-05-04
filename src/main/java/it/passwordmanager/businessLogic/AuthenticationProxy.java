package it.passwordmanager.businessLogic;

import it.passwordmanager.dao.Dao;
import it.passwordmanager.dao.LoginDao;
import it.passwordmanager.domainModel.Login;

import java.util.List;

public class AuthenticationProxy implements Dao<Login> {
    private LoginDao loginDao;
    private boolean authenticated = false;

    @Override
    public List<Login> getAll(String password) {
        if(!authenticated) {
            if(authenticate(password))
                return loginDao.getAll(password);
            else
                return null;
        }
        else
            return loginDao.getAll(password);
    }

    @Override
    public boolean create(String password, Login login) {

        return loginDao.create(password, login);
    }

    @Override
    public List<Login> read(Object obj) {

        return loginDao.read(obj);
    }

    @Override
    public boolean update(String password, Login login) {

        return loginDao.update(password, login);
    }

    @Override
    public void delete(Login login) {

        loginDao.delete(login);
    }

    private boolean authenticate(String password) {

        IdentityManager im = new IdentityManager("src/main/resources/.passwordManager.properties");
        boolean authentication = false;

        if(im.authenticate(password)) {
            loginDao = new LoginDao("jdbc:sqlite:db-login.db");
            authenticated = true;
            authentication = true;
        }

        return authentication;
    }
}
