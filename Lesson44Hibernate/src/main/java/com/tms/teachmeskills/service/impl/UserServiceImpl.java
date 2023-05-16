package com.tms.teachmeskills.service.impl;

import com.tms.teachmeskills.dao.domain.User;
import com.tms.teachmeskills.dao.impl.UserDaoImpl;
import com.tms.teachmeskills.exceptions.ServiceException;
import com.tms.teachmeskills.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDaoImpl userDao = new UserDaoImpl();

    @Override
    public void create(User user) throws ServiceException {
        userDao.save(user);
    }

    @Override
    public User read(int id) throws ServiceException {
        return userDao.getById(id);
    }

    @Override
    public void update(User user) throws ServiceException {
//        userDao.update(user);
    }

    @Override
    public void delete(User user) throws ServiceException {
        userDao.removeUser(user);
    }

    @Override
    public User getUserByEmail(String email) throws ServiceException {
        return userDao.getUserByEmail(email);
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        return userDao.getAllUsers();
    }

}
