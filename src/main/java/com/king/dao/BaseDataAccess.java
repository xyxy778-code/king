package com.king.dao;

import com.king.model.Identifiable;
import java.util.*;

public class BaseDataAccess<T extends Identifiable> implements DataAccess<T> {
    protected final Map<String, T> storage = new LinkedHashMap<>();

    @Override
    public void add(T entity) { storage.put(entity.getId(), entity); }

    @Override
    public T get(String id) { return storage.get(id); }

    @Override
    public T update(T entity) { return storage.put(entity.getId(), entity); }

    @Override
    public T delete(String id) { return storage.remove(id); }

    @Override
    public Collection<T> getAll() { return storage.values(); }

    @Override
    public int count() { return storage.size(); }
}
