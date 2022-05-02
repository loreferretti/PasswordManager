package it.passwordmanager.businessLogic;

import it.passwordmanager.dao.Dao;
import it.passwordmanager.dao.LoginDao;
import it.passwordmanager.domainModel.Login;

import java.util.List;

public class AuthenticationProxy implements Dao<Login> {
    private LoginDao loginDao;
    private IdentityManager identityManager;

    /*private void initDao() {

        loginDao = new LoginDao();

    }*/
    public AuthenticationProxy() {
        //FIXME loginDao inizializzato una volta nel costruttore e basta sennò ogni volta lo rinizializza però deve essere inizializzato nell'authenticate
        loginDao = new LoginDao();
    }

    @Override
    public List<Login> getAll(String password) {
        //initDao();

        return loginDao.getAll(password);
    }

    @Override
    public void create(String password, Login login) {
        //initDao();

        loginDao.create(password, login);
    }

    @Override
    public List<Login> read(String password, Object obj) {
        //initDao();

        return loginDao.read(password, obj);
    }

    @Override
    public void update(String password, Login login) {
        //initDao();

        loginDao.update(password, login);
    }

    @Override
    public void delete(Login login) {
        //initDao();

        loginDao.delete(login);
    }

    public boolean authenticate(String password) {

        identityManager = new IdentityManager();

        return identityManager.authenticate(password);
    }
}
