package com.teachmeskills.estore.repository;

import com.teachmeskills.estore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByLogin(String login);

    Optional<User> findUserById(Long id);

    Optional<User> findUserByLoginOrEmail(String login, String email);
}
