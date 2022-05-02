package it.passwordmanager.dao;

import java.util.List;

public interface Dao<T>{
    List<T> getAll(String password);
    void create(String password, T t);
    List<T> read(String password, Object obj);
    void update(String password, T t);
    void delete(T t);

}
