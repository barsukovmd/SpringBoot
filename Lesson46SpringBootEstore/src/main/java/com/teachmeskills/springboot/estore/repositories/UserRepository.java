package com.teachmeskills.springboot.estore.repositories;

import com.teachmeskills.springboot.estore.entities.User;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("from User")
    User findById(int id);

    User findByEmailAndPassword(String email, String password);

    @Nonnull
    List<User> findAll();
}
