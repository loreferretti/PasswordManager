package it.passwordmanager.dao;

import java.util.List;

public interface Dao<T>{
    List<T> getAll(String password);
    boolean create(String password, T t);
    List<T> read(Object obj);
    boolean update(String password, T t);
    void delete(T t);

}
