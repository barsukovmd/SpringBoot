package com.teachmeskills.springboot.estore.repositories;

import com.teachmeskills.springboot.estore.entities.Order;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    @Query("from Order")
    Order findById(int id);

    @Nonnull
    List<Order> findByDate(LocalDate date);
}
