package com.teachmeskills.springbootexample.repositories;

import com.teachmeskills.springbootexample.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("from User")
    User findById(int id);

    User findByEmailAndPassword(String email, String password);

    List<User> findAll();
}
