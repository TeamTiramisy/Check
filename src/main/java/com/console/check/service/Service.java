package com.console.check.service;

import com.console.check.dto.CardReadDto;

import java.util.List;

public interface Service<K, T, V> {

    List<T> findAll(String size, String page);

    T findById(K id);

    boolean delete(K id);

    void update(K id, V entity);

    T save(V entity);

}
