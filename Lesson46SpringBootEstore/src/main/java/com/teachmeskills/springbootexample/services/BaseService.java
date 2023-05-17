package com.teachmeskills.springbootexample.services;

import com.teachmeskills.springbootexample.entities.BaseEntity;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
    T create(T entity);

    List<T> read();

    T update(T entity);

    void delete(int id);
}
