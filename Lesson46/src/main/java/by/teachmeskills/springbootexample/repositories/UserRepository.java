package by.teachmeskills.springbootexample.repositories;

import by.teachmeskills.springbootexample.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int id);

    User findByEmailAndPassword(String email, String password);

    List<User> findAll();
}
