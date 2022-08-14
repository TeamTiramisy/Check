package com.console.check.service;

import java.util.List;

public interface CrudService<K, T, V> {

    List<T> findAll(String size, String page);

    T findById(K id);

    boolean delete(K id);

    void update(K id, V entity);

    T save(V entity);

}
