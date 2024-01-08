package com.example.man.DB.DAO;

import java.util.List;

public interface DAO <T,U>{
    void save(T o);
    void removeById(U i);
    T getById(U i);
    List<T> getAll();
    void Update(T o);
}
