package it.passwordmanager.businessLogic;

import it.passwordmanager.dao.Dao;
import it.passwordmanager.dao.LoginDao;
import it.passwordmanager.domainModel.Login;

import java.util.List;

public class AuthenticationProxy implements Dao<Login> {
    private LoginDao loginDao;
    @Override
    public List<Login> getAll() {
        return loginDao.getAll();
    }

    @Override
    public boolean create(Login login) {
        return loginDao.create(login);
    }

    @Override
    public List<Login> read(Object obj) {
        return loginDao.read(obj);
    }

    @Override
    public boolean update(Login login) {
        return loginDao.update(login);
    }

    @Override
    public boolean delete(Login login) {
        return loginDao.delete(login);
    }

    public boolean authenticate(String password) {
        return false;
    }
}
