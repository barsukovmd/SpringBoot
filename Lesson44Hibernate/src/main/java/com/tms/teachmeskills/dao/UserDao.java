package com.tms.teachmeskills.dao;

import com.tms.teachmeskills.dao.domain.User;
import java.util.List;

public interface UserDao {

    User getUserByEmail(String email);

    List<User> getAllUsers();

    User getById(int id);

    void removeUser(User user);

    List<User> findAllUsersByQueryCriteria();

}
