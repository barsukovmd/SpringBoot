package com.teachmeskills.springbootexample.repositories;

import com.teachmeskills.springbootexample.entities.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    @Query("from Order")
    Order findById(int id);

    List<Order> findByDate(LocalDateTime date);
}
