package com.tms.teachmeskills.dao;

import com.tms.teachmeskills.dao.domain.User;
import com.tms.teachmeskills.exceptions.DAOException;

import java.util.List;

public interface UserDao {

    User getUserByEmail(String email) throws DAOException;

    List<User> getAllUsers() throws DAOException;

    User getById(int id);

    void removeUser(User user);

    List<User> findAllUsersWithCriteriaQuery() throws DAOException;
}
