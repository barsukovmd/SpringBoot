package by.teachmeskills.springbootexample.repositories;

import by.teachmeskills.springbootexample.entities.Order;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    Order findById(int id);

    List<Order> findByDate(LocalDateTime date);
}
