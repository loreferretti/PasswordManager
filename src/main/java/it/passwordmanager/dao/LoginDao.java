package it.passwordmanager.dao;

import it.passwordmanager.domainModel.Login;

import java.util.List;
import java.util.function.Predicate;

public class LoginDao implements Dao<Login> {

    @Override
    public List<Login> getAll() {
        return null;
    }

    @Override
    public boolean create(Login login) {
        return false;
    }

    @Override
    public List<Login> read(Predicate predicate) {
        return null;
    }

    @Override
    public boolean update(Login login) {
        return false;
    }

    @Override
    public boolean delete(Login login) {
        return false;
    }
}
