package com.tms.teachmeskills.service;


import com.tms.teachmeskills.dao.domain.BaseEntity;
import com.tms.teachmeskills.dao.domain.User;
import com.tms.teachmeskills.exceptions.ServiceException;

public interface BaseService<T extends BaseEntity> {

    void create(T entity) throws ServiceException;

    T read(int id) throws ServiceException;

    void update(T entity) throws ServiceException;

    void delete(User user) throws ServiceException;

}
