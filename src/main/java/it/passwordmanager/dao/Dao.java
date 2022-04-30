package it.passwordmanager.dao;

import java.util.List;

public interface Dao<T>{
    public List<T> getAll();
    public boolean create(T t);
    public List<T> read(Object obj);
    public boolean update(T t);
    public boolean delete(T t);

}
