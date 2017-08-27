package com.github.adam6806.catamaranscraper.database.service;

import java.util.List;

public interface Service<T> {

    void save(T entity);

    void update(T entity);

    void delete(T entity);

    void deleteAll();

    T findById(int id);

    List<T> findAll();
}
