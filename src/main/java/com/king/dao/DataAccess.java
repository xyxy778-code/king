package com.king.dao;

import com.king.model.Identifiable;
import java.util.Collection;

public interface DataAccess<T extends Identifiable> {
    void add(T entity);
    T get(String id);
    T update(T entity);
    T delete(String id);
    Collection<T> getAll();
    int count();
}
