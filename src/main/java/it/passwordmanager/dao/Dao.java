package it.passwordmanager.dao;

import java.util.List;

public interface Dao<T>{
    List<T> getAll();
    boolean create(T t);
    List<T> read(Object obj);
    boolean update(T t);
    boolean delete(T t);

}
