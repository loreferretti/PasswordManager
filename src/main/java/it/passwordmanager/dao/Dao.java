package it.passwordmanager.dao;

import java.util.List;
import java.util.function.Predicate;

public interface Dao<T>{
    public List<T> getAll();
    public boolean create(T t);
    public List<T> read(Predicate predicate);
    public boolean update(T t);
    public boolean delete(T t);

}
