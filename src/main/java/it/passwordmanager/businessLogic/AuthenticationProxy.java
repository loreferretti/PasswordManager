package it.passwordmanager.businessLogic;

import it.passwordmanager.dao.Dao;
import it.passwordmanager.dao.LoginDao;
import it.passwordmanager.domainModel.Login;

import java.util.List;

public class AuthenticationProxy implements Dao<Login> {

    private LoginDao loginDao;
    private final String password;
    private boolean authenticated = false;
    private final String propertiesPath;
    private final String URL;

    public AuthenticationProxy(final String password, final String propertiesPath, final String URL) {
        this.password = password;
        this.propertiesPath = propertiesPath;
        this.URL = URL;
    }

    @Override
    public List<Login> getAll() {
        return authenticate(password) ? loginDao.getAll() : null;
    }

    @Override
    public boolean create(Login login) {
        return authenticate(password) && loginDao.create(login);
    }

    @Override
    public List<Login> read(Object obj) {
        return authenticate(password) ? loginDao.read(obj) : null;
    }

    @Override
    public boolean update(Login login) {
        return authenticate(password) && loginDao.update(login);
    }

    @Override
    public boolean delete(Login login) {
        return authenticate(password) && loginDao.delete(login);
    }

    private boolean authenticate(String passwd) {

        IdentityManager im = new IdentityManager(propertiesPath);

        if(!authenticated) {
            if(im.authenticate(password)) {
                loginDao = new LoginDao(password, URL);
                authenticated = true;
            }
        }
        return password.equals(passwd) && authenticated;
    }

}
