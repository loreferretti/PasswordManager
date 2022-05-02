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
    public List<Login> getAll(String password) {
        initDao();

        return loginDao.getAll(password);
    }

    @Override
    public boolean create(String password, Login login) {
        initDao();

        return loginDao.create(password, login);
    }

    @Override
    public List<Login> read(String password, Object obj) {
        initDao();

        return loginDao.read(password, obj);
    }

    @Override
    public boolean update(String password, Login login) {
        initDao();

        return loginDao.update(password, login);
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
