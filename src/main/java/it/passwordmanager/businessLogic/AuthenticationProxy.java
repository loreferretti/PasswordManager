package it.passwordmanager.businessLogic;

import it.passwordmanager.dao.Dao;
import it.passwordmanager.dao.LoginDao;
import it.passwordmanager.domainModel.Login;

import java.util.List;

public class AuthenticationProxy implements Dao<Login> {
    private LoginDao loginDao;
    private IdentityManager identityManager;

    private void initDao() {

        loginDao = new LoginDao();

    }

    @Override
    public List<Login> getAll() {
        initDao();

        return loginDao.getAll();
    }

    @Override
    public boolean create(Login login) {
        initDao();

        return loginDao.create(login);
    }

    @Override
    public List<Login> read(Object obj) {
        initDao();

        return loginDao.read(obj);
    }

    @Override
    public boolean update(Login login) {
        initDao();

        return loginDao.update(login);
    }

    @Override
    public boolean delete(Login login) {
        initDao();

        return loginDao.delete(login);
    }

    public boolean authenticate(String password) {

        identityManager = new IdentityManager();

        return identityManager.authenticate(password);
    }
}
