package com.tms.teachmeskills.service;

import com.tms.teachmeskills.dao.domain.User;
import com.tms.teachmeskills.exceptions.ServiceException;

import java.util.List;

public interface UserService extends BaseService<User> {

    User getUserByEmail(String email) throws ServiceException;

    List<User> findAllUsers() throws ServiceException;
}
