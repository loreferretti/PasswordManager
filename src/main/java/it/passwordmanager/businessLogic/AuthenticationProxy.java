package it.passwordmanager.businessLogic;

import it.passwordmanager.dao.Dao;
import it.passwordmanager.dao.LoginDao;
import it.passwordmanager.domainModel.Login;

import java.util.List;

public class AuthenticationProxy implements Dao<Login> {
    private LoginDao loginDao;
    private IdentityManager identityManager;

    @Override
    public List<Login> getAll(String password) {
        if(authenticate(password))
            return loginDao.getAll(password);
        else
            return null;
    }

    @Override
    public boolean create(String password, Login login) {

        return loginDao.create(password, login);
    }

    @Override
    public List<Login> read(String password, Object obj) {

        return loginDao.read(password, obj);
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

        identityManager = new IdentityManager();

        boolean authentication = false;

        if(identityManager.authenticate(password)) {
            loginDao = new LoginDao();
            authentication = true;
        }

        return authentication;
    }
}
