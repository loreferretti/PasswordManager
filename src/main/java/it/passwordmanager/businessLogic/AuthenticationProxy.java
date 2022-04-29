package it.passwordmanager.businessLogic;

import it.passwordmanager.dao.Dao;
import it.passwordmanager.domanModel.Login;

import java.util.List;
import java.util.function.Predicate;

public class AuthenticationProxy implements Dao<Login> {
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

    public boolean authenticate(String password) {
        return false;
    }
}
