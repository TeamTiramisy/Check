package com.console.check.service;

import com.console.check.dto.ProductReadDto;
import com.console.check.entity.Product;

import java.util.List;
import java.util.Optional;

public interface Service<K, T, V> {

    List<T> findAll(String size, String page);

    Optional<T> findById(K id);

    boolean delete(K id);

    void update(K id, V entity);

    T save(V entity);

}
