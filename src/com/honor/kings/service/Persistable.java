package com.honor.kings.service;

import java.util.List;

public interface Persistable<T> {
    boolean save(T entity);
    T findById(String id);
    boolean delete(String id);
    List<T> findAll();
}
